#pragma once

#include "PID_lib.h"

double Setpoint = 107, Input, Output;

//double Kp = 91, Ki = 0, Kd = 0;
double Kp = 35, Ki = 1200, Kd = 0.15; //15 375 0.12
PID myPID(&Input, &Output, &Setpoint, Kp, Ki, Kd, REVERSE);

uint8_t moder(uint8_t a) {
  if (a == 16 || a == 32) PORTC |= 0x01;
  else PORTC &= 0xfe;
  if (!(a == 1 || a == 4 || a == 16)) PORTC |= 0x04;
  else PORTC &= 0xfb;
  if (!(a == 1 || a == 2 || a == 16)) PORTC |= 0x02;
  else PORTC &= 0xfd;
//  digitalWrite(M3r, (b == 16 || b == 32));
//  digitalWrite(M1r, !(b == 1 || b == 4 || b == 16));
//  digitalWrite(M2r, !(b == 1 || b == 2 || b == 16));
  return a;
}

uint8_t model(uint8_t a) {
  if (a == 16 || a == 32) PORTD |= 0x40;
  else PORTD &= 0xbf;
  if (!(a == 1 || a == 4 || a == 16)) PORTB |= 0x01;
  else PORTB &= 0xfe;
  if (!(a == 1 || a == 2 || a == 16)) PORTD |= 0x80;
  else PORTD &= 0x7f;
//  digitalWrite(M3l, (a == 16 || a == 32));
//  digitalWrite(M1l, !(a == 1 || a == 4 || a == 16));
//  digitalWrite(M2l, !(a == 1 || a == 2 || a == 16));
  return a;
}
