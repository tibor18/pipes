package sk.tuke.gamestudio.game.plumber.service;

import sk.tuke.gamestudio.game.plumber.entity.Comment;

import java.util.List;

public class CommentTablePrinter {
    private CommentService commentService;

    public CommentTablePrinter(CommentService commentService) {
        this.commentService = commentService;
    }

    public void printCommentTable(String game) {
        List<Comment> comments = commentService.getComments(game);
        String header = String.format("| %-20s | %-20s | %-30s |%n", "Author", "Player", "Comment");
        String separator = String.format("+%s+%s+%s+%n", "-".repeat(22), "-".repeat(22), "-".repeat(32));

        System.out.format("Najnovšie komentáre\n");
        System.out.print(separator);
        System.out.print(header);
        System.out.print(separator);

        for (Comment comment : comments) {
            String row = String.format("| %-20s | %-20s | %-30s |%n",
                    comment.getAuthor(), comment.getName(), comment.getText());
            System.out.print(row);
        }

        System.out.print(separator);
    }
}

