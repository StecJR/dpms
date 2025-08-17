#ifndef HANDLERS_H
#define HANDLERS_H

#include <Wire.h>
#include <Adafruit_SSD1306.h>
#include <Fonts/FreeSerif9pt7b.h>
#include <Fonts/FreeSans12pt7b.h>
#include <Fonts/FreeSans18pt7b.h>
#include <WiFiEsp.h>
#include "bitmaps.h"

#define DISPLAY_WIDTH 128
#define DISPLAY_HEIGHT 64
#define DISPLAY_I2C_ADDRESS 0x3C
#define DISPLAY_RESET_PIN -1

Adafruit_SSD1306 display(DISPLAY_WIDTH, DISPLAY_HEIGHT, &Wire, DISPLAY_RESET_PIN);

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

#endif