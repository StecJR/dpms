<p align="center">
    <img alt="DPMS icon" width="150" height="150" src="./res/icon.svg" /><br>
    <img alt="Arduino" src="https://img.shields.io/badge/Project-Arduino-brightgreen?style=for-the-badge&color=6fccf3&logoColor=D9E0EE&labelColor=302D41" />
    <img alt="License" src="https://img.shields.io/github/license/StecJR/dpms?style=for-the-badge&color=8bd5ca&labelColor=302d41" />
    <img alt="Latest release" src="https://img.shields.io/github/v/release/StecJR/dpms?style=for-the-badge&color=9abaff&logoColor=d9e0ee&labelColor=302d41&sort=semver" />
    <img alt="Code size" src="https://img.shields.io/github/languages/code-size/StecJR/dpms?style=for-the-badge&color=f5e0dc&labelColor=302d41" />
    <img alt="Stars" src="https://img.shields.io/github/stars/StecJR/dpms?style=for-the-badge&color=c69ff5&labelColor=302d41" />
    <img alt="Open Issues" src="https://img.shields.io/github/issues/StecJR/dpms?style=for-the-badge&color=ee999f&labelColor=302d41" />
    <img alt="Memory usage" src="https://img.shields.io/badge/Program%20Storage-91%25-orange?style=for-the-badge&color=ddb6f2&labelColor=302d41" />
    <img alt="Memory usage" src="https://img.shields.io/badge/Dynamic%20Memory-38%25-orange?style=for-the-badge&color=c9cbff&labelColor=302d41" />
</p>

<h2><p align="center">DPMS</p></h2>

A digital system that tracks soil moisture, humidity, and temperature for the purpose of plant monitoring. Data is to be displayed locally on an OLED screen and also transmitted wirelessly via Wi-Fi to a real-time Android application.

## ✨ Features
- Real-time Sensor Data: Constantly measures soil moisture, humidity, and temperature(°C).
- Local OLED Display: Uses a SSD1306 OLED display to visualize sensor data and personalized icons.
- Wireless Data Transmission: The Wi-Fi module to send sensor data to an Android application.
- Android Companion App: A modern Jetpack Compose app for Android that allows for remote viewing of environmental data.
- Modular Codebase: For clarity and maintainability, the codebase is modular and divided into bitmaps.h, handlers.h, and Sketch.ino.
- Efficient Communication: Lightweight and quick data transfer is achieved by using TCP protocol and plain text.

> [!NOTE]
> Same project is also Implemented using HC-05 bluetooth module. For that, checkout [here](https://github.com/StecJR/dpms/tree/hc05).

## ⚙️ Setup
### 💻 Hardware Requirements
- Arduino Uno R3.
- DHT11 Sensor.
- Soil Moisture Sensor.
- SSD1306 128x64 I2C OLED Display.
- ESP8266 Serial Esp-01 Wi-Fi Module.
- ESP8266 Esp-01 Wi-Fi Module Adapter.
### 🔌 Wiring
<table>
    <tr align="center">
        <th>Sensor</th>
        <th>Sensor Pin</th>
        <th>Arduino Pin</th>
    </tr>
    <tr align="center">
        <td rowspan="3">DHT11</td>
        <td><code>+</code></td>
        <td><code>5v</code></td>
    </tr>
    <tr align="center">
        <td><code>out</code></td>
        <td><code>digital 2</code></td>
    </tr>
    <tr align="center">
        <td><code>-</code></td>
        <td><code>GND</code></td>
    </tr>
    <tr align="center">
        <td rowspan="3">Soil Moisture</td>
        <td><code>VCC</code></td>
        <td><code>5v</code></td>
    </tr>
    <tr align="center">
        <td><code>GND</code></td>
        <td><code>GND</code></td>
    </tr>
    <tr align="center">
        <td><code>A0</code></td>
        <td><code>analog A0</code></td>
    </tr>
    <tr align="center">
        <td rowspan="4">SSD1306 OLED Display</td>
        <td><code>GND</code></td>
        <td><code>GND</code></td>
    </tr>
    <tr align="center">
        <td><code>VCC</code></td>
        <td><code>5v</code></td>
    </tr>
    <tr align="center">
        <td><code>SCL</code></td>
        <td><code>analog A5</code></td>
    </tr>
    <tr align="center">
        <td><code>SDA</code></td>
        <td><code>analog A4</code></td>
    </tr>
    <tr align="center">
        <td rowspan="5">ESP8266 Wi-Fi</td>
        <td colspan="2">Insert the wifi module into the adapter accordingly</td>
    </tr>
    <tr align="center">
        <td><code>GND</code></td>
        <td><code>GND</code></td>
    </tr>
    <tr align="center">
        <td><code>VCC</code></td>
        <td><code>5v</code></td>
    </tr>
    <tr align="center">
        <td><code>TX</code></td>
        <td><code>digital 0 (RX)</code></td>
    </tr>
    <tr align="center">
        <td><code>RX</code></td>
        <td><code>digital 1 (TX)</code></td>
    </tr>
</table>

### 📦 Software Requirements
- [Arduino IDE](https://www.arduino.cc/en/software/) (or [arduino-cli](https://github.com/arduino/arduino-cli/releases/))
- [Android Studio](https://developer.android.com/studio/)
- Arduino Libraries : [`<Adafruit_SSD1306.h>`](https://docs.arduino.cc/libraries/adafruit-ssd1306/#Releases), [`<WiFiEsp.h>`](https://docs.arduino.cc/libraries/wifiesp/#Releases), [`<DHT.h>`](https://docs.arduino.cc/libraries/dht11/#Releases)

> [!NOTE]
> App required INTERNET, ACCESS_NETWORK_STATE, NEARBY_WIFI_DEVICES permissions.

> [!WARNING]
> While uploading code, disconnect the Arduino's RX and TX pins.

## 📝 Changelog
See full change log from [here](https://github.com/StecJR/dpms/blob/main/CHANGELOG.md).

## 📜 License
This repo is licensed under the [BSD 0-Clause](https://github.com/StecJR/dpms/blob/main/LICENSE) © 2025 · [Jakir Hossain](https://github.com/StecJR)