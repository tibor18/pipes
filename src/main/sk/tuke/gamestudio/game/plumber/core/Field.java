package sk.tuke.gamestudio.game.plumber.core;

import java.io.IOException;

public class Field {
    private Pipe[][] pipes;
    private int rowCount;
    private int columnCount;
    private int pipeCount;
    private boolean isGameSolved;
    private long startMillis;

    private MyLevel mylevel;

    public Field(int rowCount, int columnCount, int pipeCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.pipeCount = pipeCount;
        this.isGameSolved = false;
        if (rowCount * columnCount > pipeCount)
            throw new IllegalArgumentException("Invalid number of pipes");

        pipes = new Pipe[rowCount][columnCount];
        startMillis = System.currentTimeMillis();
    }

    public Field(int rowCount, int columnCount, int pipeCount, Level[] levels) throws IOException {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.pipeCount = pipeCount;
        this.isGameSolved = false;
        if (rowCount * columnCount > pipeCount)
            throw new IllegalArgumentException("Invalid number of pipes");

        pipes = new Pipe[rowCount][columnCount];
        startMillis = System.currentTimeMillis();
        this.fillField(mylevel);
    }

    public  int getRowCount() {
        return rowCount;
    }

    public  int getColumnCount() {
        return columnCount;
    }


    public int getPipeCount() {
        return pipeCount;
    }

    public void setSolvedGame(){
        this.isGameSolved = true;
    }

    public boolean isGameSolved(){
        return this.isGameSolved;
    }

    public Pipe[][] getPipesGrid() {
        return pipes;
    }

    public void setPlayingGame(){
        this.isGameSolved = false;
    }

    public  Pipe getPipe(int row, int column){
        return this.pipes[row][column];
    }

    public void rotatePipe(int row, int column){
        if (pipes[row][column] instanceof CurvedPipe) {
            if (pipes[row][column].getState() == PipeState.UPandRIGHT) {
                pipes[row][column].setState(PipeState.RIGHTandDOWN);
            } else if (pipes[row][column].getState() == PipeState.RIGHTandDOWN) {
                pipes[row][column].setState(PipeState.DOWNandLEFT);
            } else if (pipes[row][column].getState() == PipeState.DOWNandLEFT) {
                pipes[row][column].setState(PipeState.LEFTandUP);
            } else {
                pipes[row][column].setState(PipeState.UPandRIGHT);
            }
        } else if (pipes[row][column] instanceof StraightPipe) {
            if (pipes[row][column].getState() == PipeState.HORIZONTAL) {
                pipes[row][column].setState(PipeState.VERTICAL);
            } else {
                pipes[row][column].setState(PipeState.HORIZONTAL);
            }
        }
    }

    public void fillField(MyLevel level){
        for (int row = 0; row < this.getRowCount(); row++){
            for (int column = 0; column < this.getColumnCount(); column++){
                this.pipes[row][column] = level.getPipe(row, column);
                this.pipes[row][column].setRow(row);
                this.pipes[row][column].setColumn(column);
            }
        }
    }

    public boolean isConnected(Pipe pipe, Direction direction){
        return pipe.isConnected(direction, pipes);
    }

    public boolean isSolved() {
        boolean[][] visited = new boolean[8][8];
        if (pipes[0][0] instanceof StraightPipe && pipes[0][0].getState() != PipeState.HORIZONTAL)
            return false;
        if (pipes[0][0] instanceof CurvedPipe && pipes[0][0].getState() != PipeState.DOWNandLEFT)
            return false;
        if (pipes[7][7] instanceof StraightPipe && pipes[7][7].getState() != PipeState.HORIZONTAL)
            return false;
        if (pipes[7][7] instanceof CurvedPipe && pipes[7][7].getState() != PipeState.UPandRIGHT)
            return false;
        return dfs( 0, 0, visited);
    }

    public boolean dfs( int row, int col, boolean[][] visited) {
        if (row == 7 && col == 7) {
            return true;
        }

        if (visited[row][col]) {
            return false;
        }

        visited[row][col] = true;

        if (row < 7 && isConnected(pipes[row][col], Direction.DOWN) && dfs(row + 1, col, visited)) {
            return true;
        }
        if (row > 0 && isConnected(pipes[row][col], Direction.UP) && dfs(row - 1, col, visited)) {
            return true;
        }
        if (col < 7 && isConnected(pipes[row][col], Direction.RIGHT) && dfs(row, col + 1, visited)) {
            return true;
        }
        return col > 0 && isConnected(pipes[row][col], Direction.LEFT) && dfs(row, col - 1, visited);
    }

    public int getScore() {
        return rowCount * columnCount * 3 - (int) (System.currentTimeMillis() - startMillis) / 1000;
    }
}