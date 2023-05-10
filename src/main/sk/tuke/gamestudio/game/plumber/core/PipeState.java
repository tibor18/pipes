package sk.tuke.gamestudio.game.plumber.core;

import java.util.Random;

public enum PipeState {
    HORIZONTAL, VERTICAL, UPandRIGHT, RIGHTandDOWN, DOWNandLEFT, LEFTandUP;

    private static final PipeState states[] = values();

    private static final Random random = new Random();

    public static PipeState randomState(Pipe pipe){
        if (pipe instanceof StraightPipe){
            return states[random.nextInt(2)];
        }
        else if (pipe instanceof CurvedPipe){
            int rand = 0;
            while (rand < 2)
                rand = random.nextInt(6);
            return states[rand];
        }
        else
            return HORIZONTAL;
    }
}
