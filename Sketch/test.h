#ifndef TEST_H
#define TEST_H

#include "handlers.h"

inline void test_setup_display() {
    setup_display();
    display_banner();
    delay(2000);
    disply_ipaddress(IPAddress(192, 168, 1, 10));
    delay(2000);
}

inline void test_display() {
    display_temp_humid(30.295, 45.5);
    delay(2000);
    display_moisture(60);
    delay(2000);
}

inline void test_setup_wifi() {
    setup_wifi();
}

inline void test_broadcast() {
    broadcast("t=th,t=45.45,h=35.35");
    broadcast("t=m,m=65");
}

#endif