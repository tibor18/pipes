package sk.tuke.gamestudio.game.plumber.core;

public abstract class Pipe {
    private PipeState state;
    private int row;
    private int column;

    public void setState(PipeState state){
        this.state = state;
    }
    public PipeState getState(){
        return state;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setColumn(int column){
        this.column = column;
    }
    public int getRow(){
        return this.row;
    }
    public int getColumn(){
        return this.column;
    }
    public boolean isConnected(Direction direction, Pipe[][] pipes){
        return false;
    }
}