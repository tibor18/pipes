package sk.tuke.gamestudio.game.plumber.core;

public class StraightPipe extends Pipe {
    private PipeState state;
    private int row;
    private int column;

    public StraightPipe(){
        this.state = PipeState.randomState(this);
    }

    @Override
    public boolean isConnected(Direction direction, Pipe[][] pipes) {
        if (this.state == PipeState.VERTICAL) {
            if (direction == Direction.UP && this.row > 0) {
                if (pipes[this.row - 1][this.column] instanceof StraightPipe){
                    return pipes[this.row - 1][this.column].getState() == PipeState.VERTICAL;
                }
                else{
                    return pipes[this.row - 1][this.column].getState() == PipeState.DOWNandLEFT && pipes[this.row - 1][this.column].getState() == PipeState.RIGHTandDOWN;
                }
            }
            else if (direction == Direction.DOWN && this.row < 7){
                if (pipes[this.row + 1][this.column] instanceof StraightPipe){
                    return pipes[this.row + 1][this.column].getState() == PipeState.VERTICAL;
                }
                else{
                    return pipes[this.row + 1][this.column].getState() == PipeState.UPandRIGHT || pipes[this.row + 1][this.column].getState() == PipeState.LEFTandUP;
                }
            }
        }
        else {
            if (direction == Direction.LEFT && this.column > 0){
                if (pipes[this.row][this.column - 1] instanceof StraightPipe){
                    return pipes[this.row][this.column - 1].getState() == PipeState.HORIZONTAL;
                }
                else {
                    return pipes[this.row][this.column - 1].getState() == PipeState.RIGHTandDOWN || pipes[this.row][this.column - 1].getState() == PipeState.UPandRIGHT;
                }
            }
            else if (direction == Direction.RIGHT && this.column < 7){
                if (pipes[this.row][this.column + 1] instanceof StraightPipe){
                    return pipes[this.row][this.column + 1].getState() == PipeState.HORIZONTAL;
                }
                else {
                    return pipes[this.row][this.column + 1].getState() == PipeState.LEFTandUP || pipes[this.row][this.column + 1].getState() == PipeState.DOWNandLEFT;
                }
            }
        }
        return false;
    }

    @Override
    public void setState(PipeState state){
        this.state = state;
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

    public PipeState getState(){
        return this.state;
    }
}
