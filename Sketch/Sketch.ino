#include "test.h"

#define BAUD_RATE 115200

void setup() {
    Serial.begin(BAUD_RATE);
    test_setup_display();
    test_setup_wifi();
}

void loop() {
    test_display();
    test_broadcast();
}