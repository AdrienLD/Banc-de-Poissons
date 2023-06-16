package com.example.poissons;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
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

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    public void start(Stage stage) throws IOException {

        
        Stage premierStage = new Stage();
        




        premierStage.setTitle("Banc de Poisson");
        BorderPane root = new BorderPane();
        Button btnhello = new Button("Hello");
        btnhello.setOnAction(event -> {
            System.out.println(banc[0].x + " " + banc[0].y);
            for (Poisson poisson : banc){
                poisson.deplacer();
            }
        });
       // root.setCenter(btnhello);
        Scene scene = new Scene(root, HEIGHT, WIDTH);
        premierStage.setScene(scene);
        premierStage.show();

        for (Poisson poisson : banc) {
            Circle corpsPoisson = new Circle(3, Color.ORANGE);
            corpsPoisson.setCenterX(poisson.x);
            corpsPoisson.setCenterY(poisson.y);
            poisson.deplacer();
            root.getChildren().add(corpsPoisson);
        }


    }

    public static void main(String[] args) {
        banc = new Banc().creerbanc(100);

        launch();
    }
}