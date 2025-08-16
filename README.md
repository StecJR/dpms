<p align="center">
    <img src="./res/icon.svg" alt="DPMS icon" width="150" height="150"/><br>
</p>

<h2><p align="center">DPMS</p></h2>

A digital system that tracks soil moisture, humidity, and temperature for the purpose of plant monitoring. Data is to be displayed locally on an OLED screen and also transmitted wirelessly via Wi-Fi to a real-time Android application.

## ✨ Features
- Real-time Sensor Data: Constantly measures soil moisture, humidity, and temperature(°C).
- Local OLED Display: Uses a SSD1306 OLED display to visualize sensor data and personalized icons.
- Wireless Data Transmission: The Wi-Fi module to send sensor data to an Android application.
- Android Companion App: A modern Jetpack Compose app for Android that allows for remote viewing of environmental data.
- Modular Codebase: For clarity and maintainability, the codebase is modular and divided into bitmaps.h, handlers.h, and Sketch.ino.
- Efficient Communication: Lightweight and quick data transfer is achieved by using HTTP and plain text.

## ⚙️ Setup
### 💻 Hardware Requirements
- Arduino Uno R3.
- DHT11 Sensor.
- Soil Moisture Sensor.
- SSD1306 i2c 128x64 OLED Display.
- ESP8266 Serial Esp-01 Wi-Fi Module.
- ESP8266 Esp-01 Wi-Fi Module Adapter.
### 🔌 Wiring
DHT11 Sensor:
* Attach the `+` pin to the Arduino's `5v` pin.
* Attach the `out` pin to the Arduino's `digital 2` pin.
* Attach the `-` pin to the Arduino's `GND` pin.

Soil Moisture Sensor:
* Attach the `VCC` pin to the Arduino's `5v` pin.
* Attach the `GND` pin to the Arduino's `GND` pin.
* Attach the `AO` pin to the Arduino's `analog A0` pin.

SSD1306 OLED Display:
* Attach the `GND` pin to the Arduino's `GND` pin.
* Attach the `VCC` pin to the Arduino's `5v` pin.
* Attach the `SCL` pin to the Arduino's `analog A5` pin.
* Attach the `SDA` pin to the Arduino's `analog A4` pin.

ESP8266 Wi-Fi:
* Insert the wifi module into the adapter accordingly.
* Attach the `GND` pin to the Arduino's `GND` pin.
* Attach the `VCC` pin to the Arduino's `5v` pin.
* Attach the `TX` pin to the Arduino's `digital 0 (RX)` pin.
* Attach the `RX` pin to the Arduino's `digital 1 (TX)` pin.
### 📦 Software Requirements
- [Arduino IDE](https://www.arduino.cc/en/software/) (or [arduino-cli](https://github.com/arduino/arduino-cli/releases/))
- [Android Studio](https://developer.android.com/studio/)
- Arduino Libraries : [`<Adafruit_GFX.h>`](https://docs.arduino.cc/libraries/adafruit-gfx-library/#Releases), [`<Adafruit_SSD1306.h>`](https://docs.arduino.cc/libraries/adafruit-ssd1306/#Releases), [`<WiFiEsp.h>`](https://docs.arduino.cc/libraries/wifiesp/#Releases)
- Android Libraries : [`OkHttp`](https://square.github.io/okhttp/#releases)

> [!NOTE]
> App required INTERNET, ACCESS_NETWORK_STATE, NEARBY_WIFI_DEVICES permissions.

> [!WARNING]
> While uploading code, disconnect the Arduino's RX and TX pins.

## 📝 Changelog
See full change log from [here](https://github.com/StecJR/dpms/blob/main/CHANGELOG.md).

## 📜 License
This repo is licensed under the [BSD 0-Clause](https://github.com/StecJR/dpms/blob/main/LICENSE) © 2025 · [Jakir Hossain](https://github.com/StecJR)