package sk.tuke.gamestudio.game.plumber.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.game.plumber.entity.Score;
import sk.tuke.gamestudio.game.plumber.service.ScoreService;

import java.util.List;

//@RestController - označuje, že komponent je REST služba,
//@RequestMapping("/api/score") - URL adresa služby na serveri, na ktorej bude služba sprístupnená,
@RestController
@RequestMapping( value = "/api/score", method = { RequestMethod.GET, RequestMethod.POST })
public class ScoreServiceRest {

    @Autowired
    private ScoreService scoreService;

    //@GetMapping - metóda označená anotáciou bude reprezentovať REST metódu typu GET,
    //@PathVariable - označuje parameter metódy, ktorého hodnota bude naplnená z časti URL, v našom prípade to je názov hry, napr. z URL /api/score/mines bude hodnota parametra mines,
    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    //@PostMapping - metóda označená anotáciou bude reprezentovať REST metódu typu POST,
    //@RequestBody - označuje parameter metódy, ktorý bude naplnený z obsahu dopytu.
    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }
}
