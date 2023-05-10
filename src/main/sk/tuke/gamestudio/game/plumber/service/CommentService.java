package sk.tuke.gamestudio.game.plumber.service;


import sk.tuke.gamestudio.game.plumber.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset() throws CommentException;
}
