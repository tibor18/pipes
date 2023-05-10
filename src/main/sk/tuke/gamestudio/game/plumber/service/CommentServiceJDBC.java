package sk.tuke.gamestudio.game.plumber.service;

import sk.tuke.gamestudio.game.plumber.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService{
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String INSERT = "INSERT INTO comment(game, name, text, author, added_time) VALUES (?, ?, ?, ?, ?)";
    public static final String SELECT = "SELECT * FROM comment WHERE game = ?";
    public static final String DELETE = "DELETE comment";

    @Override
    public void addComment(Comment comment) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ){
            statement.setString(1, comment.getGame());
            statement.setString(2, comment.getName());
            statement.setString(3, comment.getText());
            statement.setString(4, comment.getAuthor());
            statement.setTimestamp(5, new Timestamp(comment.getAdded_time().getTime()));
            statement.executeUpdate();
        } catch (SQLException e){
            throw new CommentException("Problem inserting new comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5)));
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting comments", e);
        }
    }

    @Override
    public void reset() throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting comments", e);
        }
    }
}
