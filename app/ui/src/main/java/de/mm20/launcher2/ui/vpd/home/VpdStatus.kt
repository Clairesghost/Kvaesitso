package de.mm20.launcher2.ui.vpd.home

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothA2dp
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

enum class VpdConnection { None, Wifi, Mobile }

data class VpdBattery(val percent: Int, val charging: Boolean)

/**
 * Event-driven status of the three coins (AF-15, AF-19, AF-25 to AF-29). Every flow registers its
 * callback when collected and unregisters when the collector goes away, so collect them only while
 * Home is visible (repeatOnLifecycle STARTED). No polling.
 */
object VpdStatus {

    fun connection(context: Context): Flow<VpdConnection> = callbackFlow {
        val cm = context.getSystemService(ConnectivityManager::class.java)
        if (cm == null) {
            trySend(VpdConnection.None)
            close()
            return@callbackFlow
        }
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network, caps: NetworkCapabilities) {
                trySend(
                    when {
                        caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> VpdConnection.Wifi
                        caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> VpdConnection.Mobile
                        else -> VpdConnection.None
                    }
                )
            }

            override fun onLost(network: Network) {
                trySend(VpdConnection.None)
            }
        }
        trySend(VpdConnection.None)
        try {
            cm.registerDefaultNetworkCallback(callback)
        } catch (e: SecurityException) {
            Log.e("launcher", "Network callback refused", e)
        }
        awaitClose {
            try {
                cm.unregisterNetworkCallback(callback)
            } catch (e: IllegalArgumentException) {
                Log.e("launcher", "Network callback was not registered", e)
            }
        }
    }.distinctUntilChanged()

    fun hasBluetoothPermission(context: Context): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED

    /**
     * true = something is connected (headphones: A2DP / headset / ACL events). A watch that only
     * talks Bluetooth LE may not show (AF-26). Without permission this emits false and listens to nothing.
     */
    @SuppressLint("MissingPermission")
    fun bluetoothConnected(context: Context): Flow<Boolean> = callbackFlow {
        if (!hasBluetoothPermission(context)) {
            trySend(false)
            close()
            return@callbackFlow
        }
        val adapter: BluetoothAdapter? =
            context.getSystemService(BluetoothManager::class.java)?.adapter

        fun read(): Boolean {
            if (adapter == null || !adapter.isEnabled) return false
            return try {
                adapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothProfile.STATE_CONNECTED ||
                        adapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothProfile.STATE_CONNECTED
            } catch (e: SecurityException) {
                Log.e("launcher", "Bluetooth state refused", e)
                false
            }
        }

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                trySend(read())
            }
        }
        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
            addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED)
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        }
        ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_EXPORTED)
        trySend(read())
        awaitClose { context.unregisterReceiver(receiver) }
    }.distinctUntilChanged()

    fun battery(context: Context): Flow<VpdBattery> = callbackFlow {
        fun parse(intent: Intent?): VpdBattery? {
            if (intent == null) return null
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            if (level < 0 || scale <= 0) return null
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val charging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL
            return VpdBattery(level * 100 / scale, charging)
        }

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                parse(intent)?.let { trySend(it) }
            }
        }
        val sticky = ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        parse(sticky)?.let { trySend(it) }
        awaitClose { context.unregisterReceiver(receiver) }
    }.distinctUntilChanged()
}
