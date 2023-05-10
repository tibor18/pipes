package sk.tuke.gamestudio.game.plumber.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.plumber.core.Field;
import sk.tuke.gamestudio.game.plumber.service.*;

import sk.tuke.gamestudio.game.plumber.core.Level;
import sk.tuke.gamestudio.game.plumber.core.MyLevel;
import sk.tuke.gamestudio.game.plumber.entity.Comment;
import sk.tuke.gamestudio.game.plumber.entity.Rating;
import sk.tuke.gamestudio.game.plumber.entity.Score;

import java.io.IOException;

import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private static Field field;
    private final Scanner scanner = new Scanner(System.in);
    private static final Pattern INPUT_PATTERN = Pattern.compile("(([1-8])( )([1-8]))");
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    private Level[] levels;
    private int group;
    private MyLevel mylevel;
    private int count;
    private Rating rating;

    public ConsoleUI(Level[] levels){
        this.levels = levels;
        this.group = 1;
    }
    public static Field getField() {
        return field;
    }

    public void play() throws IOException {
     //   ratingService.setRating(new Rating("plumber", "tibike1", 2));
     //   System.out.println(ratingService.getRating("plumber", "tibike1"));
     //     System.out.println(commentService.getComments("plumber"));
        //commentService.addComment(new Comment("plumber", "pokus", "towajbskbfsgj", "asdfjn", new Date()));
      //  scoreService.addScore(new Score("plumber", "tvojamamka", 1111, new Date()));
     //   scoreService.getTopScores("plumber");
       // scoreService.reset();
        do {
            this.mylevel = new MyLevel(this.levels, group);
            this.field = new Field(mylevel.getWidth(), mylevel.getHeight(), mylevel.getNumberOfPipes());
            field.fillField(mylevel);
            playLevel();
        } while (!field.isGameSolved());
        rating = new Rating("plumber", System.getProperty("user.name"), this.group);
        ratingService.setRating(rating);
       // System.out.printf("Tvoja úroveň je %s.\n", ratingService.getCategory(rating));
        System.out.printf("Chceš pridať feedback?\n");
        if (endInput()){
            Scanner comment = new Scanner(System.in);
            System.out.printf("Titul: ");
            String title = comment.nextLine();
            System.out.printf("Text: ");
            String text = comment.nextLine();
            commentService.addComment(new Comment("plumber", title, text, System.getProperty("user.name"), new Date()));
        }
        CommentTablePrinter printer = new CommentTablePrinter(commentService);
        printer.printCommentTable("plumber");
        scoreService.addScore(new Score("plumber" ,System.getProperty("user.name"), field.getScore(), new Date()));

        System.exit(0);
    }

    private boolean endInput() {
        for (int i = 0; i < 3;i++) {
            var line = scanner.nextLine();
            if ("X".equals(line) || "x".equals(line)) {
                return false;
            }
            else if ("Y".equals(line) || "y".equals(line)) {
                return true;
            }
            else {
                System.out.println("Wrong input");
            }
        }
        return false;
    }

    public void playLevel(){
        this.count = 0;
        printBeforeGame();
        do {
            printField(this.field);
            //HINT DOROBIŤ!!
     /*       if (count == 15 && findCommentByName("Hint"+mylevel.getActualLevel().group) != null){
                System.out.println("HINT: " + findCommentByName("Hint"+mylevel.getActualLevel().group));
            }*/
            processInput();
            if (field.isSolved())
                field.setSolvedGame();
        } while(!field.isGameSolved());
        printField(this.field);
        System.out.println("Tvoje skore je "+ field.getScore()+".");
        scoreService.addScore(new Score("plumber" ,System.getProperty("user.name"), field.getScore(), new Date()));
        printAfterGame();
        processInputAfterSolved();
    }

    private void processInputAfterSolved() {
        for (int i = 0; i < 3;i++) {
            var line = scanner.nextLine();
            if ("X".equals(line) || "x".equals(line)) {
                break;
            }
            else if ("Y".equals(line) || "y".equals(line)) {
                field.setPlayingGame();
                this.group++;
                break;
            }
            else if ("O".equals(line) || "o".equals(line)) {
                field.setPlayingGame();
                break;
            }
            else {
                System.out.println("Wrong input");
            }
        }
    }

    private void processInput() {
        System.out.print("Zadaj svoju volbu (polohu rúry v poli): ");
        var line = scanner.nextLine();
        if ("X".equals(line) || "x".equals(line)) {
            rating = new Rating("plumber", System.getProperty("user.name"), this.group);
            ratingService.setRating(rating);
       //     System.out.printf("Nevadí, nabudúce ti to výjde! Tvoja aktuálna úroveň je %s.\n", ratingService.getCategory(rating));
            System.exit(0);
        }

        var matcher = INPUT_PATTERN.matcher(line);
        int input_row;
        int input_column;
        if (matcher.matches()){
            String[] parts;
            parts = line.split(" ");
            input_row = Integer.parseInt(parts[0]) - 1;
            input_column = Integer.parseInt(parts[1]) - 1;
            count++;
        }
        else{
            System.out.println("Wrong input");
            return;
        }
        field.rotatePipe(input_row, input_column);
    }

    private  void printField(Field field) {
        for (int row = 0; row < field.getRowCount(); row++) {
            if (row == 0)
                System.out.println("      1       2       3       4       5       6       7       8");

            System.out.printf("%d    ",row + 1);
            printPipesInRow(field, row);
            System.out.println();
            System.out.println("    –––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
        }
    }

    private void printPipesInRow(Field field, int row) {
        for (int col = 0; col < field.getColumnCount(); col++) {
            var pipe = field.getPipe(row, col);
            switch (pipe.getState()) {
                case HORIZONTAL:
                    System.out.print("–––");
                    break;
                case VERTICAL:
                    System.out.print(" | ");
                    break;
                case UPandRIGHT:
                    System.out.print("|  ");
                    break;
                case RIGHTandDOWN:
                    System.out.print(",––");
                    break;
                case DOWNandLEFT:
                    System.out.print("––,");
                    break;
                case LEFTandUP:
                    System.out.print("  |");
                    break;
                default:
                    throw new RuntimeException("Unexpected state of pipe");
            }
            System.out.print("     ");
        }
        System.out.printf("\n     ");
        for (int col = 0; col < field.getColumnCount(); col++) {
            var pipe = field.getPipe(row, col);
            switch (pipe.getState()) {
                case HORIZONTAL:
                    System.out.print("   ");
                    break;
                case VERTICAL:
                    System.out.print(" | ");
                    break;
                case UPandRIGHT:
                    System.out.print("|__");
                    break;
                case RIGHTandDOWN:
                    System.out.print("|  ");
                    break;
                case DOWNandLEFT:
                    System.out.print("  |");
                    break;
                case LEFTandUP:
                    System.out.print("__|");
                    break;
                default:
                    throw new RuntimeException("Unexpected state of pipe");
            }
            System.out.print("     ");
        }
    }

    private void printBeforeGame(){
      // System.out.println(findCommentByName("Pokyny"));
        System.out.println("Pokyny pre hranie hry Plumber: Cieľom hry je prepojiť rúry tak, aby voda pretekala z ľavého horného roha do pravého dolného roha bez toho, aby voda unikala.");
        printScoreTable(3);
        System.out.println("Ovládanie: Na otočenie ktorejkoľvek rúry zadajte jej súradnice v znení: riadok stĺpec (napr. 3 5)");
        System.out.println("Pre exit zadaj X.");
     //   System.out.println(findCommentByName("Ovládanie"));
     //   System.out.println(findCommentByName("Ovládanie2"));
    }

    private void printScoreTable(int limit) {
        var scores = scoreService.getTopScores("plumber");
        System.out.print("---------------------------------------------------------------\nBest Players:\n");
        for (int i = 0; i < scores.size(); i++){
            var score = scores.get(i);
            System.out.printf("%d. %s %d\n", i+1, score.getPlayer(), score.getPoints());
        }
        System.out.println("---------------------------------------------------------------");
    }

    private String findCommentByName(String name) {
        var comments = commentService.getComments("plumber");
        for (Comment comment: comments){
            if (Objects.equals(comment.getName(), name)){
                return comment.getText();
            }
        }
        return null;
    }

    private void printAfterGame() {
        System.out.println("Gratulujem! Vyhral si!");
        printScoreTable(5);
        System.out.println("Ak chceš pokračovať v ďalšom leveli, zadaj Y. Ak si chceš tento level zopakovať, zadaj O. Ak chceš hru ukončiť, zadaj X");
    }
}