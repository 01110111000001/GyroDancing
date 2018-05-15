float sp = 108, msp = 0;
float turn = 0;
uint16_t vcount = 0;
uint16_t tcount = 0;

bool started = false;

void serie (uint8_t send) {
  switch (send) {
    case 0:
      //      Serial.println(pid);
      Serial.print('a');
      Serial.println(Setpoint - Input);
      break;
    case 64:
      Serial.print("p");
      Serial.println(myPID.kp * (Setpoint - Input));
      break;
    case 128:
      Serial.print("i");
      Serial.println(myPID.outputSum);
      break;
    case 192:
      Serial.print("d");
      Serial.println(myPID.kd * (Input - myPID.lastInput));
      break;
  }
}

void serialEvent() {
  if (started && Serial.available() >= 3) {
    char c = Serial.read();
    uint8_t val = Serial.read();
    //    uint8_t val2 = 100;
    //    if (Serial.available()) val2 = Serial.read();
    uint8_t val2 = Serial.read();
    if (c == 'v') {
      msp = 0.01 * (val - 100);
      turn = 0.5 * (val2 - 100);
      tcount = 0;
      vcount = 0;
//      Serial.print('v');
//      Serial.print(msp);
//      Serial.print(' ');
//      Serial.println(turn);
    }
    else if (c == 'u') {
      msp = 0.02 * (val - 100);
      vcount = 0;
      Serial.print('u');
      Serial.println(msp);
    }
    else if (c == 't') {
      turn = 0.02 * (val - 100);
      tcount = 0;
      Serial.print('t');
      Serial.println(turn);
    }
    //    else if (c == 's' && val != 100) {
    //      sp += 1.0 / (val - 100);
    //      Serial.print('s');
    //      Serial.println(Setpoint);
    //    }
    //    else if (c == 'p' && val != 100) {
    //      Kp += 10.0 / (val - 100);
    //      myPID.SetTunings(Kp, Ki, Kd);
    //      Serial.print("kp");
    //      Serial.println(Kp);
    //    }
    //    else if (c == 'i') {
    //      if (val != 100) {
    //        Ki += 100.0 / (val - 100);
    //        myPID.SetTunings(Kp, Ki, Kd);
    //        Serial.print("ki");
    //        Serial.println(Ki);
    //      }
    //      //      else myPID.outputSum = 0;
    //    }
    //    else if (c == 'd' && val != 100) {
    //      Kd += 0.1 / (val - 100);
    //      myPID.SetTunings(Kp, Ki, Kd);
    //      Serial.print("kd");
    //      Serial.println(Kd);
    //    }
  }
}
