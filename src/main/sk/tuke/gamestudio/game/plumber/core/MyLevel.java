package sk.tuke.gamestudio.game.plumber.core;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MyLevel {
    private final List<Pipe> pipes;
    private final int width;
    private final int height;
    private final int numberOfPipes;
    private Level ActualLevel;

    public MyLevel(Level[] filenames, int group) throws IOException {
        this.pipes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(getRandomFileName(filenames, group)));
        String line = reader.readLine();
        this.numberOfPipes = Integer.parseInt(line);
        while (line != null) {
            String[] parts;
            parts = line.split(" ");
            for (String part : parts) {
                if (part.equals("S")) {
                    pipes.add(new StraightPipe());
                } else if (part.equals("C")) {
                    pipes.add(new CurvedPipe());
                }
            }
            line = reader.readLine();
        }
        reader.close();

        this.width = (int) Math.sqrt(pipes.size());
        this.height = (int) Math.sqrt(pipes.size());
    }

    public Pipe getPipe(int x, int y) {
        if (y*width+x < pipes.toArray().length)
            return pipes.get(y * width + x);

        return new CurvedPipe();
    }

    public  String getRandomFileName(Level[] fileNames, int group) {
        Random random = new Random();
        List<Level> filenamesByGroup = new ArrayList<>();
        for (Level filename : fileNames){
            if (filename.getGroup() == group){
                filenamesByGroup.add(filename);
            }
        }
        int randomIndex = random.nextInt(filenamesByGroup.size());
        randomIndex = 1;
    //    return fileNames[randomIndex];
        this.ActualLevel = filenamesByGroup.get(randomIndex);
        return filenamesByGroup.get(randomIndex).getFilename();
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getNumberOfPipes(){
        return this.numberOfPipes;
    }
    public Level getActualLevel() {
        return this.ActualLevel;
    }
}