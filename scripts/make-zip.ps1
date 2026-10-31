# Zips the kit folder next to itself: kit/launcher-studio-kit.zip (Launcher Studio Kit, CC BY 4.0).
# Run from anywhere:  powershell -ExecutionPolicy Bypass -File scripts\make-zip.ps1
# Mac/Linux: from the kit folder's parent run:  zip -r launcher-studio-kit.zip launcher-studio-kit -x "*/.git/*"
$ErrorActionPreference = "Stop"
$kit = Split-Path -Parent $PSScriptRoot
$zip = Join-Path (Split-Path -Parent $kit) "launcher-studio-kit.zip"
if (Test-Path $zip) { Remove-Item $zip -Force }
Add-Type -AssemblyName System.IO.Compression.FileSystem
$staging = Join-Path ([System.IO.Path]::GetTempPath()) ("lsk-" + [guid]::NewGuid().ToString("N"))
New-Item -ItemType Directory -Path $staging | Out-Null
try {
    # copy to staging so hidden folders (.claude) are included and the top folder is named launcher-studio-kit
    $dest = Join-Path $staging "launcher-studio-kit"
    Copy-Item -Path $kit -Destination $dest -Recurse -Force
    [System.IO.Compression.ZipFile]::CreateFromDirectory($dest, $zip, [System.IO.Compression.CompressionLevel]::Optimal, $true)
} finally {
    Remove-Item $staging -Recurse -Force -ErrorAction SilentlyContinue
}
$size = [math]::Round((Get-Item $zip).Length / 1KB, 1)
Write-Host "Wrote $zip ($size KB)"
