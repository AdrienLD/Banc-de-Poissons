package com.example.poissons;


import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Poisson {
    public double vitesse;
    public double x;
    public double y;
    public double direction;
    public static final double angleVision = 220;
    public static final double distanceVision = 40;
    public Arc vuePoisson;
    public Polygon corpsPoisson;

    public Poisson(int vitesse, int x, int y, int direction) {
        this.vitesse = vitesse;
        this.x = x;
        this.y = y;
        this.direction = direction;

    }

    public void creerVue(){
        double angleDebut = direction - angleVision / 2;
        double angleArc = angleVision;
        if (angleArc < 0) {
            angleArc += 360;
        }
        vuePoisson = new Arc();
        vuePoisson.setCenterX(x);
        vuePoisson.setCenterY(y);
        vuePoisson.setRadiusX(distanceVision);
        vuePoisson.setRadiusY(distanceVision);
        vuePoisson.setStartAngle(-angleDebut);
        vuePoisson.setLength(-angleArc);
        vuePoisson.setFill(Color.BLUE);
        vuePoisson.setStroke(Color.TRANSPARENT);
        vuePoisson.setType(ArcType.ROUND);
    }



    public List<Poisson> getvisibles(Poisson actuel) {
        List<Poisson> visibles = new ArrayList<>();
        double angleArc = angleVision;
        if (angleArc < 0) {
            angleArc += 360;
        }
        double angleDebut = direction - angleVision / 2;
        for (Poisson autrepoisson : HelloApplication.banc) {
            if (autrepoisson == actuel) {
                continue;
            }
            double dx = autrepoisson.x - actuel.x;
            double dy = autrepoisson.y - actuel.y;
            double distanceSquared = dx * dx + dy * dy;
            if (distanceSquared > distanceVision * distanceVision) {
                continue;
            }
            double angleToPoint = Math.toDegrees(Math.atan2(autrepoisson.y - y, autrepoisson.x - x));
            if (angleToPoint < 0) {
                angleToPoint += 360;
            }
            double angleFin = angleDebut + angleArc;
            if (angleDebut <= angleToPoint && angleToPoint <= angleFin) {
                this.vuePoisson.setFill(Color.RED);
                visibles.add(autrepoisson);
            }
        }
        return visibles;
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
        double cosAngle = Math.cos(angleRadians);
        double sinAngle = Math.sin(angleRadians);
        x += vitesse * cosAngle;
        y += vitesse * sinAngle;
        List<Poisson> poissonsvisibles = getvisibles(this);

        Poisson proches = null;
        List<Poisson> bonnedistance = new ArrayList<Poisson>();
        List<Poisson> troploin = new ArrayList<Poisson>();
        for (Poisson aproximite : poissonsvisibles){
            if (aproximite == this){
                continue;
            }
            double dx = aproximite.x - x;
            double dy = aproximite.y - y;
            double distanceSquared = dx * dx + dy * dy;
            distanceSquared = Math.sqrt(distanceSquared);
            if (distanceSquared < distanceVision / 2) {
                this.vuePoisson.setFill(Color.ORANGE);
                if (proches == null) {
                    proches = aproximite;
                } else {
                    double px = proches.x - x;
                    double py = proches.y - y;
                    double pdistanceSquared = dx * dx + dy * dy;
                    pdistanceSquared = Math.sqrt(pdistanceSquared);
                    if (pdistanceSquared > distanceSquared) {
                        proches = aproximite;
                    }
                }
            } else if (distanceSquared < 2 * distanceVision / 3){
                this.vuePoisson.setFill(Color.GREEN);
                bonnedistance.add(aproximite);
            } else {
                troploin.add(aproximite);
            }
        }

        if (Banc.systemeGuidage == 0){
            guidageDemande(proches, bonnedistance, troploin);
        } else if (Banc.systemeGuidage == 1){
            guidageBoids(proches, bonnedistance, troploin);
        }
    }

    public void guidageDemande(Poisson proches, List<Poisson> bonnedistance, List<Poisson> troploin){
        if (proches != null) {
            double dx = proches.x - x;
            double dy = proches.y - y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double angleToProches = Math.toDegrees(Math.atan2(dy, dx));
            double relativeAngle = angleToProches - this.direction;
            if (relativeAngle > 180) {
                relativeAngle -= 360;
            } else if (relativeAngle < -180) {
                relativeAngle += 360;
            }
            double forcevitesse = 1 / distance;
            direction -= relativeAngle * forcevitesse;
            vitesse += (4 - vitesse) * forcevitesse;
        }
        if (bonnedistance.size() > 0){
            double sommeX = 0, sommeY = 0, vitessesomme = 0;
            for (Poisson poissonbonnedistance : bonnedistance) {
                sommeX += poissonbonnedistance.x;
                sommeY += poissonbonnedistance.y;
                vitessesomme += poissonbonnedistance.vitesse;
            }
            double moyenneX = sommeX / bonnedistance.size();
            double moyenneY = sommeY / bonnedistance.size();
            double moyenneVitesse = vitessesomme / bonnedistance.size();
            double dx = moyenneX - x;
            double dy = moyenneY - y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double angleToCentre = Math.toDegrees(Math.atan2(dy, dx));
            double relativeAngle = angleToCentre - this.direction;
            if (relativeAngle > 180) {
                relativeAngle -= 360;
            } else if (relativeAngle < -180) {
                relativeAngle += 360;
            }

            double forcevitesse = 1 / distance;
            direction += relativeAngle * forcevitesse;
            vitesse += (moyenneVitesse - vitesse) * forcevitesse;
        }
        if (troploin.size() > 0) {
            vuePoisson.setFill(Color.PURPLE);
            double sommeX = 0, sommeY = 0;
            for (Poisson poissonloin : troploin) {
                sommeX += poissonloin.x;
                sommeY += poissonloin.y;
            }
            double moyenneX = sommeX / troploin.size();
            double moyenneY = sommeY / troploin.size();
            double dx = moyenneX - x;
            double dy = moyenneY - y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double angleToCentre = Math.toDegrees(Math.atan2(dy, dx));
            double relativeAngle = angleToCentre - this.direction;
            if (relativeAngle > 180) {
                relativeAngle -= 360;
            } else if (relativeAngle < -180) {
                relativeAngle += 360;
            }

            double forcevitesse = 4 / distance;
            direction += relativeAngle * forcevitesse;
            if (vitesse < 4) {
                vitesse += vitesse * forcevitesse;
            }
        }
    }



    public void guidageBoids(Poisson proches, List<Poisson> bonnedistance, List<Poisson> troploin){
        if (proches != null) {
            double dx = proches.x - x;
            double dy = proches.y - y;
            double angleToProches = Math.toDegrees(Math.atan2(dy, dx));
            if (angleToProches < 0) angleToProches += 360;
            double relativeAngle = angleToProches - direction;
            if (relativeAngle > 180) relativeAngle -= 360;
            if (relativeAngle < -180) relativeAngle += 360;
            if (relativeAngle < 0) {
                direction += 2;
            } else {
                direction -= 2;
            }
            if (vitesse > 1){
                vitesse -= 0.1;
            }
        } else if (bonnedistance.size() > 0){
            double angle = 0;
            double leurvitesse = 0;
            for (Poisson poissonbonnedistance : bonnedistance){
                angle += poissonbonnedistance.direction;
                leurvitesse += poissonbonnedistance.vitesse;
            }
            direction += ((angle / bonnedistance.size()) - direction) * 0.1;
            vitesse += ((leurvitesse / bonnedistance.size()) - vitesse) * 0.5;
        } else if (troploin.size() > 0){
            this.vuePoisson.setFill(Color.YELLOW);
            double sommeX = 0, sommeY = 0;
            for (Poisson poissonloin : troploin){
                sommeX += poissonloin.x;
                sommeY += poissonloin.y;
            }
            double moyenneX = sommeX / troploin.size();
            double moyenneY = sommeY / troploin.size();
            double dx = moyenneX - x;
            double dy = moyenneY - y;
            double angleToProches = Math.toDegrees(Math.atan2(dy, dx));
            if (angleToProches < 0) angleToProches += 360;
            double relativeAngle = angleToProches - direction;
            if (relativeAngle > 180) relativeAngle -= 360;
            if (relativeAngle < -180) relativeAngle += 360;
            if (relativeAngle > 0) {
                direction += 0.5;
            } else {
                direction -= 0.5;
            }
            vitesse += 0.1;
        }
    }
}


