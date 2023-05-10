package sk.tuke.gamestudio.game.plumber.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.game.plumber.entity.Rating;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) {
        TypedQuery<Double> query = entityManager.createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game", Double.class);
        query.setParameter("game", game);
        Double averageRating = query.getSingleResult();
        return (averageRating == null) ? 0 : averageRating.intValue();
    }

    @Override
    public int getRating(String game, String player) {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT r.rating FROM Rating r WHERE r.game = :game AND r.player = :player", Integer.class);
        query.setParameter("game", game);
        query.setParameter("player", player);
        Integer rating = query.getSingleResult();
        return (rating == null) ? 0 : rating;
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createQuery("delete from Rating").executeUpdate();
    }
}
