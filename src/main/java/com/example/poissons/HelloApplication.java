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
    static final int NOMBRE_POISSONS = 200;
    static final int WIDTH = 900;
    static final int HEIGHT = 900;
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
        root.getChildren().add(poisson.vuePoisson);

    }
}