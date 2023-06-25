package com.example.poissons;


public class Poisson {
    public int vitesse;
    public double x;
    public double y;
    public int direction;
    public static double angleVision = 30;
    public static double distanceVision = 40;


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
        if (x < 0 || x > HelloApplication.WIDTH) {
            direction = 180 - direction;
        }
        if (y < 0 || y > HelloApplication.HEIGHT) {
            direction = 360 - direction;
        }
        if (direction < 0) {
            direction += 360;
        }
        double angleRadians = Math.toRadians(direction);
        x += (double) (vitesse * Math.cos(angleRadians));
        y += (double) (vitesse * Math.sin(angleRadians));
    }

}
