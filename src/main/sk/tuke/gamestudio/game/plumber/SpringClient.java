package sk.tuke.gamestudio.game.plumber;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.plumber.consoleui.ConsoleUI;

import sk.tuke.gamestudio.game.plumber.core.Level;
import sk.tuke.gamestudio.game.plumber.service.*;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.game.plumber.*"))
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);}

    @Bean
    public CommandLineRunner runner(ConsoleUI console){
        return s -> console.play();
    }

    @Bean
    public ConsoleUI console(Level[] level){
        return new ConsoleUI(level);
    }

    @Bean
    public Level[] levels() {
        Level[] levels = {new Level("src/main/resources/level1.txt", 1), new Level("src/main/resources/level2.txt", 1), new Level("src/main/resources/level3.txt", 2), new Level("src/main/resources/level4.txt", 2), new Level("src/main/resources/level5.txt", 3)};
        return levels;
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceRestClient();
    }

    @Bean
    public CommentService commentService(){ return new CommentServiceRestClient(); }

    @Bean
    public RatingService ratingService(){ return new RatingServiceRestClient(); }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
