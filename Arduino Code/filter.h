double angles[] = {0, 0, 0, 0};
double coeffs[] = {0, 0, 0, 1};
//double coeffs[] = {0, 0, 0.9, 0.1};
//double coeffs[] = {0.1, 0.2, 0.3, 0.4};
//double somme = 1;

double filtre() {
  for (uint8_t i = 0; i < 3; i++) angles[i] = angles[i + 1];
  angles[3] = mpuLoop();
  double in = 0;
  for (uint8_t i = 0; i < 4; i++) in += coeffs[i] * angles[i];
  return in;
}
