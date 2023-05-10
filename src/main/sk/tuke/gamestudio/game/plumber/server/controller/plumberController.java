package sk.tuke.gamestudio.game.plumber.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.tuke.gamestudio.game.plumber.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.plumber.core.*;

import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/plumber")
public class plumberController {

    private MyLevel mylevel;
    private Level[] levels;

    @RequestMapping
    public String plumber(){
        return "plumber";
    }

    private Field field;


    public String getHtmlField() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>");
            for (int col = 0; col < field.getColumnCount(); col++) {
                sb.append("<td>");
                sb.append(getHtmlPipe(field.getPipesGrid()[row][col]));
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    private String getHtmlPipe(Pipe pipe) {
        StringBuilder sb = new StringBuilder();
        sb.append("<img src='images/plumber/");
        sb.append(getImageName(pipe));
        sb.append("'>");
        return sb.toString();
    }

    public static String getImageName(Pipe pipe) {
        if (pipe instanceof StraightPipe) {
            if (pipe.getState() == PipeState.HORIZONTAL) {
                return "pipe_horizontal.png";
            } else {
                return "pipe_vertical.png";
            }
        } else if (pipe instanceof CurvedPipe) {
            if (pipe.getState() == PipeState.UPandRIGHT) {
                return "pipe_up_right.png";
            } else if (pipe.getState() == PipeState.RIGHTandDOWN) {
                return "pipe_right_down.png";
            } else if (pipe.getState() == PipeState.DOWNandLEFT) {
                return "pipe_down_left.png";
            } else {
                return "pipe_left_up.png";
            }
        } else {
            throw new IllegalArgumentException("Unknown pipe type: " + pipe.getClass().getName());
        }
    }

}
