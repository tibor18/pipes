package sk.tuke.gamestudio.game.plumber.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.game.plumber.entity.Comment;

import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);

    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return entityManager.createNamedQuery("Comment.getComments")
                .setParameter("game", game)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Comment.resetComments").executeUpdate();
    }

}
