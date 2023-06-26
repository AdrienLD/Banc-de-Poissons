package com.example.poissons;


import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Poisson {
    public int vitesse;
    public double x;
    public double y;
    public int direction;
    public static double angleVision = 30;
    public static double distanceVision = 40;
    public Polygon vuePoisson;

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

    public void creerVue(){
        vuePoisson = new Polygon();
        double angleRadian;
        angleRadian = Math.toRadians(direction - angleVision / 2);
        double xA = x + distanceVision * Math.cos(angleRadian);
        double yA = y + distanceVision * Math.sin(angleRadian);

        // Calcul des coordonnées du deuxième point du triangle (point B)
        angleRadian = Math.toRadians(direction + angleVision / 2);
        double xB = x + distanceVision * Math.cos(angleRadian);
        double yB = y + distanceVision * Math.sin(angleRadian);
        vuePoisson.getPoints().addAll(
                x, y,  // Coordonnées du poisson (point de base du triangle)
                xA, yA,                // Coordonnées du point A
                xB, yB                 // Coordonnées du point B
        );
        vuePoisson.setFill(Color.BLUE);
        vuePoisson.setStroke(Color.TRANSPARENT);
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
        creerVue();
        double angleRadians = Math.toRadians(direction);
        x += (double) (vitesse * Math.cos(angleRadians));
        y += (double) (vitesse * Math.sin(angleRadians));
        for (Poisson autrespoissons : HelloApplication.banc) {
            if (autrespoissons == this) {
                continue;
            } else if (vuePoisson.contains(autrespoissons.x, autrespoissons.y)) {
                double dx = autrespoissons.x - x;
                double dy = autrespoissons.y - y;
                double distance = Math.sqrt(dx * dx + dy * dy);
                double angleAbsolu = Math.toDegrees(Math.atan2(dy, dx));
                double angleRelatif = angleAbsolu - direction;
                if (distance < distanceVision/2) {
                    if (angleRelatif < 0) {
                        direction += 2;
                    } else {
                        direction -= 2;
                    }
                    vuePoisson.setFill(Color.RED);
                } else {
                    if (angleRelatif < 0.3) {
                        direction -= 0.5;
                    } else if (angleRelatif > 0.3) {
                        direction += 0.5;
                    }
                    vuePoisson.setFill(Color.GREEN);
                }
            }

        }
    }

}
