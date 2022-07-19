package com.example.gameoflife;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Menu extends ToolBar {

    private final MainView mainView;
    private HBox boxy;

    public Menu(MainView mainView) {
        this.mainView = mainView;
        Button run = new Button("Run");
        run.setOnAction(this::handleRun);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        Button clear = new Button("Clear");
        clear.setOnAction(this::handleClear);
        Button exit = new Button("Exit");
        exit.setOnAction(e -> Platform.exit());

        this.getItems().addAll(run, stop, clear,exit, this.getContent());
    }



    private void handleClear(ActionEvent actionEvent) {
        if (mainView.getAppState() == MainView.RUNNING) {
            return;
        }
        mainView.resetBoard();
        mainView.draw();

    }

    private Parent getContent() {
        Label label1 = new Label("                 Stay alive    =   (1,...,n)     ");
        TextField textField1 = new TextField();
        textField1.setMaxSize(90,90);
        textField1.setText("2,3");

        Label label2 = new Label("                             New life    =   (1,...,n)     ");
        TextField textField2 = new TextField();
        textField2.setMaxSize(90,90);
        textField2.setText("3");

        boxy = new HBox(label1, textField1, label2, textField2);
        boxy.setAlignment(Pos.BASELINE_CENTER);
        return boxy;
    }

    private int[] getRuleValueAtIndex(int index) {
        Node out = boxy.getChildren().get(index);
        int[] numbers = null;
        if(out instanceof TextField) {
            String input = ((TextField) out).getText();
            String[] numStrings = input.split(",");

            numbers = new int[numStrings.length];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Integer.parseInt(numStrings[i]);
            }
        }
        return numbers;
    }

    private void handleStop(ActionEvent actionEvent) {
        this.mainView.setAppState(MainView.EDITING);
        this.mainView.getSimulator().stop();
    }

    private void handleRun(ActionEvent actionEvent) {
        this.mainView.setAppState(MainView.RUNNING);

        int[] stayAliveRules = getRuleValueAtIndex(1);
        int[] becomeAliveRules = getRuleValueAtIndex(3);

        this.mainView.getSimulator().setRulesValues(stayAliveRules, becomeAliveRules);
        this.mainView.getSimulator().start();
    }
}
