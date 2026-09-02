#!/usr/bin/env python3
from datetime import datetime
import urllib.request
import json
now = datetime.now()
time_text = now.strftime("%H:%M:%S")
lat =  55.40
lon =  37.16

url =  f"https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current=temperature_2m,surface_pressure,weather_code&timezone=auto"
with urllib.request.urlopen(url, timeout=5) as response:
    data = response.read().decode('utf-8')

weather_data = json.loads(data)
current = weather_data['current']

temp = current['temperature_2m']
pres = current['surface_pressure']
code = current['weather_code']

temp = round(temp, 1)
davka = round(pres * 0.75006)

if code == 0:
    icon = '☀ ясно'
elif code == 1:
    icon = '🌤 в основном ясно'
elif code == 2:
    icon = '⛅ переменная облачность'
elif code == 3:
    icon = '☁ пасмурно'
elif 45 <= code <= 48:
    icon = '🌫 туман'
elif 51 <= code <= 65:
    icon = '🌧 дождь'
elif code in (66, 67):
    icon = '❄ ледяной дождь'
elif code in (71, 73):
    icon = '❄ слабый снег'
elif code in (75, 77):
    icon = '❄ сильный снег'
elif 80 <= code <= 82:
    icon = '🌧 ливень'
elif code in (85, 86):
    icon = '❄ ливневый снег'
elif code == 95:
    icon = '⛈ гроза'
elif 96 <= code <= 99:
    icon = '⛈ гроза с градом'
else:
    icon = '• неизвестно'
text = f"{time_text} | {temp}°C · {davka} мм {icon}"
tooltip = f"{time_text}\n🌡 {temp}°C\n🧭 {davka} мм\n{icon}"
waybar_data = {
    "text": text,
    "tooltip": tooltip
}
print(json.dumps(waybar_data, ensure_ascii=False))
