package sk.tuke.gamestudio.game.plumber.service;

import sk.tuke.gamestudio.game.plumber.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score) throws ScoreException;
    List<Score> getTopScores(String game) throws ScoreException;
    void reset() throws ScoreException;
}
