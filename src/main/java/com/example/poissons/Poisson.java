package com.example.poissons;


import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

public class Poisson {
    public double vitesse;
    public double x;
    public double y;
    public double direction;
    public static final double angleVision = 120;
    public static final double distanceVision = 40;
    public Arc vuePoisson;

    public Poisson(int vitesse, int x, int y, int direction) {
        this.vitesse = vitesse;
        this.x = x;
        this.y = y;
        this.direction = direction;

    }

    public void creerVue(){
        // Plutôt que de calculer l'angle entre l'axe des x positifs et chaque point,
        // nous calculons directement l'angle de début et l'angle de fin
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
        double avgDirection = 0;
        double avgX = 0, avgY = 0;
        double sepX = 0, sepY = 0;
        int visibleCount = poissonsvisibles.size();
        for (Poisson aproximite : poissonsvisibles){
            avgDirection += aproximite.direction;
            avgX += aproximite.x;
            avgY += aproximite.y;

            double dx = x - aproximite.x;
            double dy = y - aproximite.y;
            double distanceSquared = dx * dx + dy * dy;
            if (distanceSquared  < Math.pow(distanceVision/3, 2)) {
                sepX += dx / distanceSquared;
                sepY += dy / distanceSquared;
            }

        }
        if (visibleCount > 0){
            avgX /= visibleCount;
            avgY /= visibleCount;
            avgDirection /= visibleCount;
            double dx = avgX - x;
            double dy = avgY - y;
            double targetDirection = Math.toDegrees(Math.atan2(dy, dx));
            if (targetDirection < 0) targetDirection += 360;
            double targetSpeed = Math.sqrt(dx * dx + dy * dy);
            double sepDirection = Math.toDegrees(Math.atan2(sepY, sepX));
            vitesse = (targetSpeed - vitesse) * 0.1;
            if (sepDirection < 0) sepDirection += 360;
            if (sepX != 0 && sepY != 0 && distanceVision < 0) {
                direction += ((sepDirection + avgDirection) / 2 - direction) * 0.1;
            } else {
                direction += ((targetDirection  + avgDirection) / 2- direction) * 0.1;
            }
        }
    }

}
