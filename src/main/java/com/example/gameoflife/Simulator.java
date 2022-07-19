package com.example.gameoflife;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {

    private final Timeline timeline;
    private final MainView mainView;
    private final Board board;

    private int[] _stayAliveRules;
    private int[] _becomeAliveRules;

    public Simulator(MainView mainView, Board board) {
        this.mainView = mainView;
        this.board = board;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(100), this::doIterate));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doIterate(ActionEvent actionEvent) {
        this.board.iterate(_stayAliveRules, _becomeAliveRules);
        this.mainView.draw();
    }

    public void setRulesValues(int[] stayAliveRules, int[] becomeAliveRules) {
        _stayAliveRules = stayAliveRules;
        _becomeAliveRules = becomeAliveRules;
    }

    public void start(){
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }
}
