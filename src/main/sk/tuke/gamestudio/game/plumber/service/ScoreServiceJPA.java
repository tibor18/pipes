package sk.tuke.gamestudio.game.plumber.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.game.plumber.entity.Score;

import java.util.List;
@Transactional
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) throws ScoreException {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException {
        return entityManager.createNamedQuery("Score.getTopScores")
                .setParameter("game", game)
                .setMaxResults(3)
                .getResultList();
                /*
        return (List<Score>) entityManager.createQuery("select s from Score s where s.game = :game order by s.points desc")
                .setParameter("game", game)
                .setMaxResults(3)
                .getResultList();
         */

    }

    @Override
    public void reset() {
         entityManager.createNamedQuery("Score.resetScores").executeUpdate();
    }
}

