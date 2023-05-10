package sk.tuke.gamestudio.game.plumber.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private int ident;
    private String game;
    private String player;
    private int rating;

    public Rating() {

    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String author) {
        this.player = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Rating(String game, String player, int rating){
        this.game = game;
        this.player = player;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                '}';
    }
    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }
}
