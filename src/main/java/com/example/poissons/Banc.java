package com.example.poissons;

import java.util.Arrays;
import java.util.Random;

public class Banc {
    Poisson[] banc = new Poisson[100];

    public Poisson[] creerbanc(int nombrePoissons) {
        Banc monBanc = new Banc();

        for (int i = 0; i < nombrePoissons; i++) {
            Random random = new Random();

            int vitesseAleatoire = random.nextInt(5) + 1;
            int xAleatoire = random.nextInt(50,100);
            int yAleatoire = random.nextInt(50,100);
            int directionAleatoire = random.nextInt(360);

            monBanc.banc[i] = new Poisson(vitesseAleatoire, xAleatoire, yAleatoire, directionAleatoire);
        }
        return monBanc.banc;
    }
}
