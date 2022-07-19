package com.example.gameoflife;

public class Board {

    public static final int DEAD = 0;
    public static final int ALIVE = 1;

    int width;
    int height;
    int[][] board;
    private static final int[][] NEIGHBOURS = {
            {-1, -1}, {-1, 0}, {-1, +1},
            { 0, -1},          { 0, +1},
            {+1, -1}, {+1, 0}, {+1, +1}};

    public Board(int x, int y) {
        this.width = x;
        this.height = y;
        this.board = new int[width][height];
    }

    public void setAlive(int x, int y) {
        this.board[x][y] = ALIVE;
    }

    public void setDead(int x, int y) {
        this.board[x][y] = DEAD;
    }

    public  boolean isAlive(int x, int y) {
        //making the board "infinite"
        if (x < 0) {
            x = width-1;
        } else if (x >= width) {
            x = 0;
        }
        if ( y < 0) {
            y = height -1;
        } else if (y >= height) {
            y = 0;
        }
        return this.board[x][y]==ALIVE;
    }

    public int countNeighbours(int x, int y) {
        int count = 0;
        for (int[] offset : NEIGHBOURS) {
            if (isAlive(x + offset[1], y+offset[0])) {
                count++;
            }
        }
        return count;
    }

    public void iterate(int[] stayAliveRules, int[] becomeAliveRules) {
        int[][] newBoard = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int aliveNeighbours = countNeighbours(x,y);
                if (board[x][y] == ALIVE) {
                    for (int i : stayAliveRules) {
                        if(aliveNeighbours == i) {
                            newBoard[x][y] = ALIVE;
                            break;
                        } else {
                            newBoard[x][y] = DEAD;
                        }
                    }

                } else {
                    for (int i : becomeAliveRules) {
                        if (aliveNeighbours == i) {
                            newBoard[x][y] = ALIVE;
                            break;
                        } else {
                            newBoard[x][y] = DEAD;
                        }
                    }
                }
            }
        }
        this.board = newBoard;
    }
}
