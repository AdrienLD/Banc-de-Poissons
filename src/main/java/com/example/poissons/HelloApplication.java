package com.example.poissons;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HelloApplication extends Application {
    static Poisson[] banc;
    static final int NOMBRE_POISSONS = 100;
    static final int WIDTH = 900;
    static final int HEIGHT = 900;
    private static final double ARROW_SIZE = 20.0;
    public void start(Stage stage) throws IOException {
        Stage premierStage = new Stage();
        premierStage.setTitle("Première fenêtre");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, HEIGHT, WIDTH);
        premierStage.setScene(scene);
        premierStage.show();
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
        timer.start();
    }

    public static void main(String[] args) {
        banc = new Banc().creerbanc(NOMBRE_POISSONS);

        launch();
    }

    private void creerVuePoisson(Pane root, Poisson poisson) {
        poisson.corpsPoisson = new Polygon(
                poisson.x, poisson.y,
                poisson.x - ARROW_SIZE, poisson.y - ARROW_SIZE / 2,
                poisson.x - ARROW_SIZE, poisson.y + ARROW_SIZE / 2
        );
        Rotate rotation = new Rotate(poisson.direction, poisson.x, poisson.y);
        poisson.corpsPoisson.getTransforms().add(rotation);
        Line ligneDirection = new Line(poisson.x, poisson.y, poisson.x + 50 * Math.cos(Math.toRadians(poisson.direction)), poisson.y + 50 * Math.sin(Math.toRadians(poisson.direction)));
        root.getChildren().add(poisson.vuePoisson);
        root.getChildren().add(poisson.corpsPoisson);
        root.getChildren().add(ligneDirection);


    }
}