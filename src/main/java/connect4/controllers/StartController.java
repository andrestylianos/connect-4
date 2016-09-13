package connect4.controllers;

import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.services.BoardService;
import connect4.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "start", method = RequestMethod.GET, produces = "application/json")
    public BoardState startGame(@RequestParam("size") BoardSize boardSize) {

        BoardState initialBoardState = boardService.initializeBoard(BoardSize.SIZE_DEFAULT);
        redisService.upsertValue(initialBoardState.getId().toString(), initialBoardState);
        return initialBoardState;
    }

}
