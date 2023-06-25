package com.example.poissons;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HelloApplication extends Application {
    static Poisson[] banc;
    static final int NOMBRE_POISSONS = 20;
    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    public void start(Stage stage) throws IOException {
        Stage premierStage = new Stage();
        premierStage.setTitle("Première fenêtre");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, HEIGHT, WIDTH);
        premierStage.setScene(scene);
        premierStage.show();

        // Créer et ajouter les poissons
        for (Poisson poisson : banc) {
            Circle corpsPoisson = new Circle(3, Color.ORANGE);
            corpsPoisson.setCenterX(poisson.x);
            corpsPoisson.setCenterY(poisson.y);
            root.getChildren().add(corpsPoisson);
        }

        // Créer un AnimationTimer pour déplacer les poissons indéfiniment
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                root.getChildren().clear();
                for (Poisson poisson : banc) {

                    poisson.deplacer();
                    creerVuePoisson(root, poisson);
                }
            }
        };

        // Démarrer l'AnimationTimer
        timer.start();
    }

    public static void main(String[] args) {
        banc = new Banc().creerbanc(NOMBRE_POISSONS);

        launch();
    }

    private void creerVuePoisson(Pane root, Poisson poisson) {
        Circle corpsPoisson = new Circle(3, Color.ORANGE);
        corpsPoisson.setCenterX(poisson.x);
        corpsPoisson.setCenterY(poisson.y);
        root.getChildren().add(corpsPoisson);
        double angleRadian;

        // Calcul des coordonnées du premier point du triangle (point A)
        angleRadian = Math.toRadians(poisson.direction - poisson.angleVision / 2);
        double xA = poisson.x + poisson.distanceVision * Math.cos(angleRadian);
        double yA = poisson.y + poisson.distanceVision * Math.sin(angleRadian);

        // Calcul des coordonnées du deuxième point du triangle (point B)
        angleRadian = Math.toRadians(poisson.direction + poisson.angleVision / 2);
        double xB = poisson.x + poisson.distanceVision * Math.cos(angleRadian);
        double yB = poisson.y + poisson.distanceVision * Math.sin(angleRadian);

        // Création du triangle en utilisant les coordonnées des points
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                poisson.x, poisson.y,  // Coordonnées du poisson (point de base du triangle)
                xA, yA,                // Coordonnées du point A
                xB, yB                 // Coordonnées du point B
        );

        // Paramétrage du style du triangle
        triangle.setFill(Color.GREEN);
        triangle.setStroke(Color.TRANSPARENT);

        for (Poisson autrespoissons : banc){
            if (autrespoissons == poisson){
                continue;
            }
            if (triangle.contains(autrespoissons.x, autrespoissons.y)){
                double dx = autrespoissons.x - poisson.x;
                double dy = autrespoissons.y - poisson.y;
                double distance = Math.sqrt(dx * dx + dy * dy);
                double angleAbsolu = Math.toDegrees(Math.atan2(dy, dx));
                double angleRelatif = angleAbsolu - poisson.direction;
                if (angleRelatif < -180) angleRelatif += 360;
                if (angleRelatif > 180) angleRelatif -= 360;
                if (angleRelatif > 0){
                    poisson.direction += 0.5;
                } else if (angleRelatif < 0) {
                    poisson.direction -= 0.5;
                }
                triangle.setFill(Color.RED);
            }
        }


        // Ajout du triangle au conteneur
        root.getChildren().add(triangle);
    }
}