#pragma once

class Timer_ {
  public:
    Timer();
    void timerSetup(long t);
};
extern Timer_ Timer;

void Timer_::timerSetup(long t = 20) {
  TCCR2A = 0;               //Need to be set to 0
  TCCR2B = 0;               //Need to be set to 0
  TIMSK2 |= (1 << OCIE2A);  //Interrupt Enable
  TCCR2B |= (1 << CS21);    //Prescaler 8
  OCR2A = 2 * t - 1;           //Compare register 39 for 20µs
  TCCR2A |= (1 << WGM21);   //CTC mode
}
