package com.example.poissons;

public class Poisson {
    public int vitesse;
    public int x;
    public int y;
    public int direction;
    public static double angleVision = 30;
    public static double distanceVision = 10;


    public static void setDistanceVision(double distanceVision) {
        Poisson.distanceVision = distanceVision;
    }
    public static void setAngleVision(double angleVision) {
        Poisson.angleVision = angleVision;
    }
    public Poisson(int vitesse, int x, int y, int direction) {
        this.vitesse = vitesse;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public void deplacer() {
        double angleRadians = Math.toRadians(direction);
        int nouveauX = x + (int) (vitesse * Math.cos(angleRadians));
        int nouveauY = y + (int) (vitesse * Math.sin(angleRadians));
        while (nouveauX < 0 || nouveauX > 100 || nouveauY < 0 || nouveauY > 100) {
            direction ++;
            angleRadians = Math.toRadians(direction);
            nouveauX = x + (int) (vitesse * Math.cos(angleRadians));
            nouveauY = y + (int) (vitesse * Math.sin(angleRadians));
        }
        x = nouveauX;
        y = nouveauY;
    }

}
