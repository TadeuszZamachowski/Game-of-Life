package com.example.gameoflife;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    public static final int EDITING = 0;
    public static final int RUNNING = 1;

    public static final int X_SIZE = (int) ( 1920);//resolution
    public static final int Y_SIZE = (int) (1080);

    private static final int X_BOARD_SIZE = X_SIZE/10;
    private static final int Y_BOARD_SIZE = Y_SIZE/10;

    private final Canvas canvas;
    private Board board;

    private final Affine affine;

    private Simulator simulator;

    private int appState = EDITING;

    public MainView() {
        System.out.println("X >  "+ X_BOARD_SIZE);
        System.out.println("Y >  "+ Y_BOARD_SIZE);

        canvas = new Canvas(X_SIZE,Y_SIZE);
        canvas.setOnMousePressed(this::handleDraw);

        Menu menu = new Menu(this);

        this.getChildren().addAll(menu,canvas);

        this.affine = new Affine();
        affine.appendScale(X_SIZE / (float) X_BOARD_SIZE, Y_SIZE/(float) Y_BOARD_SIZE);
        board = new Board(X_BOARD_SIZE, Y_BOARD_SIZE);
    }

    private void handleDraw(MouseEvent mouseEvent)  {

        if(appState == RUNNING) {
            return;
        }

        Point2D simCord = this.getSimCoordinates(mouseEvent);

        int simX = (int)simCord.getX();
        int simY = (int)simCord.getY();

        if (board.isAlive(simX, simY)) {
            board.setDead(simX, simY);
        } else {
            board.setAlive(simX, simY);
        }

        draw();
    }

    private Point2D getSimCoordinates(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        Point2D simCord = null;
        try {
            simCord = this.affine.inverseTransform(mouseX, mouseY);
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        return simCord;
    }

    public void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,X_SIZE,Y_SIZE);

        if (this.appState == EDITING) {
            drawSim(board);
        } else {
            drawSim(board);
        }

        g.setStroke(Color.GREY);
        g.setLineWidth(0.05f);
        for (int x = 0; x <= this.board.width; x++) {
            g.strokeLine(x,0,x, Y_BOARD_SIZE);
        }

        for (int y = 0; y <= this.board.width; y++) {
            g.strokeLine(0,y, X_BOARD_SIZE,y);
        }
    }

    private void drawSim(Board simDraw) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.RED);
        for (int x = 0; x < simDraw.width; x++) {
            for (int y = 0; y < simDraw.height; y++) {
                if(simDraw.isAlive(x,y)) {
                    g.fillRect(x,y,1,1);
                }
            }
        }
    }

    public void setAppState(int appState) {
        if (appState == this.appState) {
            return;
        }
        if(appState == RUNNING) {
            this.simulator = new Simulator(this, this.board);
        }

        this.appState = appState;
    }

    public int getAppState() {
        return appState;
    }


    public Simulator getSimulator() {
        return simulator;
    }

    public void resetBoard() {
        board = new Board(X_BOARD_SIZE, Y_BOARD_SIZE);
    }
}
