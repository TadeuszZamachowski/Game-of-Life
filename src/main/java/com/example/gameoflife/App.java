package com.example.gameoflife;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage)  {
        MainView mainView = new MainView();
        int x = MainView.X_SIZE;
        int y = MainView.Y_SIZE;
        Scene scene = new Scene(mainView, x, y+35);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        mainView.draw();
    }

    public static void main(String[] args) {
        launch();
    }
}