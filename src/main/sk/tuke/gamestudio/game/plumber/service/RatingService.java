package sk.tuke.gamestudio.game.plumber.service;


import sk.tuke.gamestudio.game.plumber.entity.Rating;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;
    int getAverageRating(String game) throws RatingException;
    int getRating(String game, String player) throws RatingException;
    void reset() throws RatingException;
}
