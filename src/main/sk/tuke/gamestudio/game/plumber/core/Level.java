package sk.tuke.gamestudio.game.plumber.core;

public class Level {

    public Level(String filename, int group){
        this.filename = filename;
        this.group = group;
    }

    public String getFilename() {
        return filename;
    }

    public int getGroup() {
        return group;
    }

    public String filename;

    public int group;
}
