#include "handlers.h"
// #include "test.h"

void setup() {
#if DEBUG_MODE
    Serial.begin(9600);
    SWSerial.begin(BAUD_RATE);
#else
    Serial.begin(BAUD_RATE);
#endif
    // test_setup_display();

    setup_display();
    display_banner();
    setup_sensors();
    delay(2000);
}

void loop() {
    // test_display();
    // test_broadcast();
    
    handle_temp_humid();
    handle_moisture();
}