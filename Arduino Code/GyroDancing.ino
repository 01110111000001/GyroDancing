#include "define.h"
#include "timer.h"
#include "mpu.h"
#include "pid.h"
#include "filter.h"
#include "serial.h"

float pid = 0;

uint32_t countr = 0;
float thmemr = 50;
uint16_t thr = 50;
uint8_t mr = 16;

uint32_t countl = 0;
float thmeml = 50;
uint16_t thl = 50;
uint8_t ml = 16;

uint32_t start;

uint8_t send = 0;

void setup() {
  Setpoint = sp + msp;
  start = millis();
  Serial.begin(115200);
  DDRD |= 0xF0;
  DDRB |= 0x1F;
  DDRC |= 0x0F;
  PORTD |= 0xE0;
  PORTB |= 0x17;
  PORTC |= 0x0F;
  moder(8);
  model(8);
  mpuSetup();
  myPID.SetOutputLimits(-400, 400);
  myPID.SetSampleTime(4);
  while (millis() < start + wait) {
    mpuLoop();
    delay(2);
  }
  start += 2 * wait;
  myPID.SetMode(AUTOMATIC);
  Timer.timerSetup();
  digitalWrite(enabler, LOW);
  digitalWrite(enablel, LOW);
  myPID.outputSum=0;
}

void loop() {
  Input = filtre();
  Setpoint = sp + msp;
  if (vcount < 250) vcount++;
  else msp = 0;
  if (tcount < 250) tcount++;
  else turn = 0;
  send += 16;
  serie(send);
  myPID.Compute();
  if (Output < 2 && Output > -2) Output = 0;
  if (Output > 0) pid = -5 + 5500.0 / (Output + 9);
  else if (Output < 0) pid = 5 + 5500.0 / (Output - 9);
  else pid = 0;
  if (millis() < start && msp == 0) {
    if (pid < 0)sp += 0.00005;
    else if (pid > 0)sp -= 0.00005;
  }
  else if (!started) started = true;
  thmemr = pid;
  thmeml = pid;
  if (thmemr < 0) {
    thmemr *= -1;
    PORTB &= 0xFB;
  } else PORTB |= 0x04;
  if (thmeml < 0) {
    thmeml *= -1;
    PORTD |= 0x20;
  } else PORTD &= 0xDF;
  thr = thmemr - turn;
  thl = thmeml + turn;
}

ISR(TIMER2_COMPA_vect) {
  countr++;
  if (countr > thr) {
    countr = 0;
    PORTB |= 0x08;
  } else if (countr == 1) PORTB &= 0xF7;

  countl++;
  if (countl > thl) {
    countl = 0;
    PORTD |= 0x10;
  } else if (countl == 1) PORTD &= 0xEF;
}
