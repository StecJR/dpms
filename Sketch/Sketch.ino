#include "handlers.h"
// #include "test.h"

void setup() {
    Serial.begin(BAUD_RATE);
#if DEBUG_MODE
    SWSerial.begin(BAUD_RATE);
#endif
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