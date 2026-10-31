#!/bin/bash
# usage: render.sh "name1 name2" width height  -> renders name.html to name.png with headless Edge
D="$(cd "$(dirname "$0")" && pwd -W)"
for n in $1; do
PROF="C:/Users/clair/AppData/Local/Temp/edge-prof-$RANDOM"
powershell -NoProfile -Command "& 'C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe' --headless=new --disable-gpu --hide-scrollbars --user-data-dir='$PROF' --window-size=$2,$3 --virtual-time-budget=8000 --screenshot='$D/$n.png' 'file:///$D/$n.html' 2>&1 | Out-Null"
done
