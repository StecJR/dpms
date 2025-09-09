#ifndef TEST_H
#define TEST_H

#include "handlers.h"

inline void test_setup_display() {
    setup_display();
    display_banner();
    disply_ipaddress(IPAddress(192, 168, 1, 10));
    delay(2000);
}

inline void test_display() {
    display_temp_humid("30.2", "45.5");
    delay(BROADCAST_TIME);
    display_moisture((uint8_t) 60);
    delay(BROADCAST_TIME);
}

inline void test_setup_wifi() {
    setup_wifi();
}

inline void test_broadcast() {
    broadcast("t=th,t=45.4,h=35.3");
    broadcast("t=m,m=65");
}

#endif