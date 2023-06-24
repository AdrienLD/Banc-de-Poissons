package com.example.poissons;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class HelloApplication extends Application {
    static Poisson[] banc;
    static final int NOMBRE_POISSONS = 300;
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
                    Circle corpsPoisson = new Circle(3, Color.ORANGE);
                    corpsPoisson.setCenterX(poisson.x);
                    corpsPoisson.setCenterY(poisson.y);
                    root.getChildren().add(corpsPoisson);
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
}