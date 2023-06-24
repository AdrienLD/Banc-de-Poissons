package com.example.poissons;

import java.util.Arrays;
import java.util.Random;

public class Banc {
    Poisson[] banc = new Poisson[HelloApplication.NOMBRE_POISSONS];

    public Poisson[] creerbanc(int nombrePoissons) {
        Banc monBanc = new Banc();

        for (int i = 0; i < nombrePoissons; i++) {
            Random random = new Random();

            int vitesseAleatoire = random.nextInt(1) + 1;
            int xAleatoire = random.nextInt(HelloApplication.WIDTH);
            int yAleatoire = random.nextInt(HelloApplication.HEIGHT);
            int directionAleatoire = random.nextInt(360);

            monBanc.banc[i] = new Poisson(vitesseAleatoire, xAleatoire, yAleatoire, directionAleatoire);
        }
        return monBanc.banc;
    }
}
