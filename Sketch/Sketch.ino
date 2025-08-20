#include "handlers.h"
// #include "test.h"

#define BAUD_RATE 115200

void setup() {
    Serial.begin(BAUD_RATE);
    setup_display();
    display_banner();
    setup_wifi();
    setup_sensors();
    delay(2000);

    // test_setup_display();
    // test_setup_wifi();
}

void loop() {
    handle_temp_humid();
    handle_moisture();

    // test_display();
    // test_broadcast();
}