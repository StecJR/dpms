#ifndef HANDLERS_H
#define HANDLERS_H

#include <Wire.h>
#include <Adafruit_SSD1306.h>
#include <Fonts/FreeSerif9pt7b.h>
#include <Fonts/FreeSans12pt7b.h>
#include <Fonts/FreeSans18pt7b.h>
#include <WiFiEsp.h>
#include <DHT.h>
#include "bitmaps.h"

#define DISPLAY_WIDTH 128
#define DISPLAY_HEIGHT 64
#define DISPLAY_I2C_ADDRESS 0x3C
#define DISPLAY_RESET_PIN -1
#define BROADCAST_TIME 2000
#define DHT_PIN 2
#define DHT_TYPE DHT11
#define MOISTURE_PIN A0
#define MOISTURE_WET_LEVEL 300
#define MOISTURE_DRY_LEVEL 700

static const char wifi_ssid[] PROGMEM = "DPMS JR";
static const char wifi_pass[] PROGMEM = "dpms1234";

Adafruit_SSD1306 display(DISPLAY_WIDTH, DISPLAY_HEIGHT, &Wire, DISPLAY_RESET_PIN);
WiFiEspServer wifi_server(80);
DHT dht(DHT_PIN, DHT_TYPE);

inline void setup_display() {
    if (!display.begin(SSD1306_SWITCHCAPVCC, DISPLAY_I2C_ADDRESS)) {
        Serial.println(F("Display setup failed"));
        for (;;);
    }
    display.setTextWrap(false);
    display.setTextColor(WHITE);
}

inline void display_banner() {
    display.clearDisplay();
    display.drawBitmap(0, 0, bitmap_banner, DISPLAY_WIDTH, DISPLAY_HEIGHT, WHITE);
    display.display();
}

inline void disply_ipaddress(IPAddress addr) {
    display.setTextSize(1);
    display.setCursor(50, 56);
    display.setTextColor(WHITE, BLACK);
    display.print(addr);
    display.display();
}

inline void display_temp_humid(float temp, float humid) {
    char buf[8];

    display.clearDisplay();
    display.setTextSize(1);
    display.drawBitmap(6, 4, bitmap_temp, 24, 24, WHITE);
    display.setCursor(40, 0);
    display.print(F("Temperature :"));
    dtostrf(temp, 0, 1, buf);
    display.setCursor(40, 20);
    display.setFont(&FreeSans12pt7b);
    display.print(buf);
    display.setFont();
    display.setCursor(92, 12);
    display.setTextSize(2);
    display.print((char)247);
    display.print(F("C"));

    display.drawBitmap(8, 36, bitmap_humid, 24, 24, WHITE);
    display.setTextSize(1);
    display.setCursor(40, 32);
    display.print(F("Humidity :"));
    dtostrf(humid, 0, 1, buf);
    display.setCursor(40, 52);
    display.setFont(&FreeSans12pt7b);
    display.print(buf);
    display.setFont();
    display.setCursor(92, 44);
    display.setTextSize(2);
    display.print(F("%"));
    display.display();
}

inline void display_moisture(uint8_t moisture) {
    display.clearDisplay();
    display.setTextSize(1);
    display.setCursor(34, 10);
    display.setFont(&FreeSerif9pt7b);
    display.print(F("Moisture"));
    display.drawBitmap(14, 25, bitmap_moist, 32, 32, WHITE);
    display.setCursor(54, 52);
    display.setFont(&FreeSans18pt7b);
    display.print(moisture);
    display.setFont();
    display.setCursor(100, 36);
    display.setTextSize(2);
    display.print("%");
    display.display();
}

inline void setup_wifi() {
    WiFi.init(&Serial);
    if (WiFi.status() == WL_NO_SHIELD) {
        for (;;);
    }

    IPAddress wifi_ip(192, 168, 1, 1);
    WiFi.configAP(wifi_ip);
    WiFi.beginAP(wifi_ssid, 11, wifi_pass, ENC_TYPE_WPA_WPA2_PSK);
    disply_ipaddress(WiFi.localIP());
    wifi_server.begin();
}

void broadcast(String content) {
    RingBuffer buffer(8);
    WiFiEspClient client;
    unsigned long starting_time = millis();

    while (millis() - starting_time < BROADCAST_TIME) {
        client = wifi_server.available();
        if (client) {
            buffer.init();
            while (client.connected()) {
                if (client.available()) {
                    char c = client.read();
                    buffer.push(c);
                    if (buffer.endsWith("\r\n\r\n")) {
                        client.print(
                            "HTTP/1.1 200 OK\r\n"
                            "Content-Type: text/plain\r\n"
                            "Connection: close\r\n\r\n"
                        );
                        client.print(content);
                        break;
                    }
                }
            }
            delay(10);
            client.stop();
        }
    }
}

inline void setup_sensors() {
    dht.begin();
}

void handle_temp_humid() {
    float temp = dht.readTemperature();
    float humid = dht.readHumidity();
    if (isnan(temp) || isnan(humid)) {
        return;
    }

    display_temp_humid(temp, humid);
    broadcast("t=th,t=" + String(temp) + ",h=" + String(humid));
}

void handle_moisture() {
    uint8_t moisture = constrain(
        map(analogRead(MOISTURE_PIN), MOISTURE_DRY_LEVEL, MOISTURE_WET_LEVEL, 0, 100),
        0,
        100
    );

    display_moisture(moisture);
    broadcast("t=m,m=" + String(moisture));
}

#endif