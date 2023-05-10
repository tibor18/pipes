package sk.tuke.gamestudio.game.plumber;

import sk.tuke.gamestudio.game.plumber.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.plumber.core.Field;
import sk.tuke.gamestudio.game.plumber.core.Level;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Level[] levels = {new Level("src/main/resources/level1.txt", 1), new Level("src/main/resources/level2.txt", 1), new Level("src/main/resources/level3.txt", 2), new Level("src/main/resources/level4.txt", 2), new Level("src/main/resources/level5.txt", 3)};
        var field = new Field(8,8,64, levels);
        var ui = new ConsoleUI(levels);
        ui.play();
    }
}
