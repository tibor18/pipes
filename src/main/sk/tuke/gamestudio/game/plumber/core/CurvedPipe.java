package sk.tuke.gamestudio.game.plumber.core;

public class CurvedPipe extends Pipe {
    private PipeState state;
    private int row;
    private int column;

    public CurvedPipe(){
        this.state = PipeState.randomState(this);
    }

    public CurvedPipe(PipeState state){
        this.state = state;
    }

    public void setState(PipeState state){
        this.state = state;
    }

    public PipeState getState(){
        return this.state;
    }

    public void setRow(int row) {
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

    public boolean isConnected(Direction direction, Pipe[][] pipes) {
        if (direction == Direction.UP){
            if ((this.state == PipeState.UPandRIGHT || this.state == PipeState.LEFTandUP) && this.row > 0){
                if (pipes[row - 1][column] instanceof StraightPipe && pipes[row - 1][column].getState() == PipeState.VERTICAL){
                    return true;
                }
                else if (pipes[row - 1][column] instanceof  CurvedPipe){
                    return pipes[row - 1][column].getState() == PipeState.DOWNandLEFT || pipes[row - 1][column].getState() == PipeState.RIGHTandDOWN;
                }
            }
        }
        else if(direction == Direction.DOWN){
            if ((this.state == PipeState.DOWNandLEFT|| this.state == PipeState.RIGHTandDOWN) && this.row < 7){
                if (pipes[row + 1][column] instanceof StraightPipe && pipes[row + 1][column].getState() == PipeState.VERTICAL){
                    return true;
                }
                else if (pipes[row + 1][column] instanceof CurvedPipe){
                    return pipes[row + 1][column].getState() == PipeState.UPandRIGHT || pipes[row + 1][column].getState() == PipeState.LEFTandUP;
                }
            }
        }
        else if (direction == Direction.LEFT){
            if ((this.state == PipeState.LEFTandUP || this.state == PipeState.DOWNandLEFT) && this.column > 0){
                if (pipes[row][column - 1] instanceof StraightPipe && pipes[row][column - 1].getState() == PipeState.HORIZONTAL){
                    return true;
                }
                else if(pipes[row][column - 1] instanceof CurvedPipe){
                    return pipes[row][column - 1].getState() == PipeState.RIGHTandDOWN || pipes[row][column - 1].getState() == PipeState.UPandRIGHT;
                }
            }
        }
        else{
            if ((this.state == PipeState.RIGHTandDOWN || this.state == PipeState.UPandRIGHT) && this.column < 7){
                if (pipes[row][column + 1] instanceof StraightPipe && pipes[row][column + 1].getState() == PipeState.HORIZONTAL){
                    return true;
                }
                else if (pipes[row][column + 1] instanceof CurvedPipe){
                    return pipes[row][column + 1].getState() == PipeState.LEFTandUP || pipes[row][column + 1].getState() == PipeState.DOWNandLEFT;
                }
            }
        }
        return false;
    }
}
