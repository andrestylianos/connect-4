package connect4.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {

    @RequestMapping(value = "start", method = RequestMethod.GET)
    public String startGame() {
        return "Working endpoint";
    }

}
