#ifndef HANDLERS_H
#define HANDLERS_H

#include <Adafruit_SSD1306.h>
#include <Wire.h>
#include <DHT.h>
#include "bitmaps.h"

#define DEBUG_MODE 0
#define BAUD_RATE 9600
#if DEBUG_MODE
#include <SoftwareSerial.h>
SoftwareSerial SWSerial(6, 7); // RX, TX
#endif
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

Adafruit_SSD1306 display(DISPLAY_WIDTH, DISPLAY_HEIGHT, &Wire, DISPLAY_RESET_PIN);
DHT dht(DHT_PIN, DHT_TYPE);

inline void setup_display() {
    if (!display.begin(SSD1306_SWITCHCAPVCC, DISPLAY_I2C_ADDRESS)) {
#if DEBUG_MODE
        Serial.println(F("SSD1306 allocation failed"));
#endif
        for (;;);
    }
    display.setTextColor(WHITE);
}

inline void display_banner() {
    display.clearDisplay();
    display.drawBitmap(0, 0, bitmap_banner, DISPLAY_WIDTH, DISPLAY_HEIGHT, WHITE);
    display.display();
}

void display_temp_humid(const char *temp, const char *humid) {
    display.clearDisplay();
    display.setTextSize(1);
    display.drawBitmap(8, 4, bitmap_temp, 24, 24, WHITE);
    display.setCursor(40, 0);
    display.print(F("Temperature:"));
    display.setCursor(92, 18);
    display.print((char) 247);
    display.print(F("C"));
    display.setTextSize(2);
    display.setCursor(40, 11);
    display.print(temp);

    display.setTextSize(1);
    display.drawBitmap(10, 36, bitmap_humid, 24, 24, WHITE);
    display.setCursor(40, 32);
    display.print(F("Humidity:"));
    display.setCursor(94, 50);
    display.print(F("%"));
    display.setTextSize(2);
    display.setCursor(40, 43);
    display.print(humid);
    display.display();
}

void display_moisture(uint8_t moisture) {
    display.clearDisplay();
    display.setTextSize(2);
    display.drawBitmap(16, 26, bitmap_moist, 32, 32, WHITE);
    display.setCursor(14, 7);
    display.print(F("Moisture:"));
    display.setCursor(98, 38);
    display.print("%");
    display.setTextSize(3);
    display.setCursor(56, 32);
    display.print(moisture);
    display.display();
}

void broadcast(const char *content) {
#if DEBUG_MODE
    if (SWSerial.available()) {
        SWSerial.println(content);
        SWSerial.flush();
        Serial.print("Sent: ");
        Serial.println(content);
#else
    if (Serial.available()) {
        Serial.println(content);
        Serial.flush();
#endif
        delay(BROADCAST_TIME);
    }
}

inline void setup_sensors() {
    dht.begin();
}

inline void handle_temp_humid() {
    float temp = dht.readTemperature();
    float humid = dht.readHumidity();
    if (isnan(temp) || isnan(humid)) return;

    char temp_buff[8], humid_buff[8], content[22];
    dtostrf(temp, 0, 1, temp_buff);
    dtostrf(humid, 0, 1, humid_buff);
    display_temp_humid(temp_buff, humid_buff);
    snprintf(content, sizeof(content), "t=th,t=%s,h=%s", temp_buff, humid_buff);
    broadcast(content);
}

inline void handle_moisture() {
    uint8_t moisture = constrain(
            map(analogRead(MOISTURE_PIN), MOISTURE_DRY_LEVEL, MOISTURE_WET_LEVEL, 0, 100),
            0,
            100
    );

    display_moisture(moisture);
    char content[10];
    snprintf(content, sizeof(content), "t=m,m=%u", (unsigned) moisture);
    broadcast(content);
}

#endif