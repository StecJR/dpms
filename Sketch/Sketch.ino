// #include "handlers.h"
#include "test.h"

#define BAUD_RATE 115200

void setup() {
    Serial.begin(BAUD_RATE);
    test_setup_display();
}

void loop() {
    test_display();
}