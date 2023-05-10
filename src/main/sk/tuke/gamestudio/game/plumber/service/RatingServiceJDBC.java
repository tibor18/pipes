package sk.tuke.gamestudio.game.plumber.service;

import sk.tuke.gamestudio.game.plumber.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService{
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT game, player, rating FROM rating WHERE game = ? AND player = ?";
    public static final String SELECT_AVERAGE = "SELECT game, player, rating FROM rating WHERE game = ?";
    public static final String SELECT_CATEGORY = "SELECT name FROM category WHERE game = ? AND id_category = ?";

    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating) VALUES (?, ?, ?)";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRating());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Problem setting rating", e);
        }
    }

    public String getCategory(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY)
        ){
            statement.setString(1, rating.getGame());
            statement.setInt(2, rating.getRating());
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getString(1);
            }
    } catch (SQLException e) {
        throw new ScoreException("Problem getting category", e);
    }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_AVERAGE);
        ){
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                int sum = 0;
                int count = 0;
                while (rs.next()){
                    sum += rs.getInt(3);
                    count++;
                }
                return sum/count;
            }
        } catch (SQLException e){
            throw new RatingException("Problem getting average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ){
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getInt(3);
            }
        } catch (SQLException e){
            throw new RatingException("Problem getting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting rating", e);
        }
    }
}
