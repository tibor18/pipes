package sk.tuke.gamestudio.game.plumber.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.util.Date;
@Entity
@NamedQuery( name = "Comment.getComments",
        query = "SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.added_time DESC ")
@NamedQuery( name = "Comment.resetComments",
        query = "DELETE FROM Comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    private String game;
    private String name;
    private String text;
    private String author;
    private Date added_time;

    public Comment(String game, String name, String text, String author, Date added_time){
        this.game = game;
        this.name = name;
        this.text = text;
        this.author = author;
        this.added_time = added_time;
    }

    public Comment() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getAdded_time() {
        return added_time;
    }

    public void setAdded_time(Date added_time) {
        this.added_time = added_time;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
    @Override
    public String toString() {
        return "Comment{" +
                "name='" + this.name + '\'' +
                ", text='" + this.text + '\'' +
                ", author=" + this.author +
                ", added at=" + this.added_time +
                '}';
    }
    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }

}
