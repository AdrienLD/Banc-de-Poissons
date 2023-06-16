package com.example.poissons;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static Poisson[] banc;

    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    public void start(Stage stage) throws IOException {
        Stage premierStage = new Stage();
        premierStage.setTitle("Première fenêtre");
        BorderPane root = new BorderPane();
        Button btnhello = new Button("Hello");
        btnhello.setOnAction(event -> {
            System.out.println(banc[0].x + " " + banc[0].y);
            for (Poisson poisson : banc){
                poisson.deplacer();
            }
        });
        root.setCenter(btnhello);
        Scene scene = new Scene(root, 300, 250);
        premierStage.setScene(scene);
        premierStage.show();


    }

    public static void main(String[] args) {
        banc = new Banc().creerbanc(100);

        launch();
    }
}