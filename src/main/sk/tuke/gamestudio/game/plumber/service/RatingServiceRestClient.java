package sk.tuke.gamestudio.game.plumber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.plumber.entity.Comment;
import sk.tuke.gamestudio.game.plumber.entity.Rating;
import sk.tuke.gamestudio.game.plumber.entity.Score;

import java.util.List;

public class RatingServiceRestClient implements RatingService{

    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        String queryUrl = url + "/" + game;
        ResponseEntity<Integer> response = restTemplate.getForEntity(queryUrl, Integer.class);
        return response.getBody();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        String queryUrl = url + "/" + game + "/" + player;
        ResponseEntity<Integer> response = restTemplate.getForEntity(queryUrl, Integer.class);
        return response.getBody();
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
