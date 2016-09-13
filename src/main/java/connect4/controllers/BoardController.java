package connect4.controllers;

import connect4.ai.Player;
import connect4.ai.RandomPlayer;
import connect4.enums.GameState;
import connect4.exceptions.FinishedGameException;
import connect4.exceptions.InconsistentBoardStateException;
import connect4.exceptions.InvalidMoveException;
import connect4.models.BoardState;
import connect4.models.PlayerMove;
import connect4.services.BoardService;
import connect4.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(path = "play/{boardId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<BoardState> play(@PathVariable String boardId, @RequestBody PlayerMove move){

        BoardState boardState = redisService.getValue(UUID.fromString(boardId).toString(), BoardState.class);

        if(boardState == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            boardState = boardService.doPlayerMove(move, boardState);
            if(boardState.getGameState() != GameState.ACTIVE){
                redisService.upsertValue(boardState.getId().toString(), boardState);
                return ResponseEntity.ok(boardState);
            }
        } catch (InvalidMoveException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (FinishedGameException e) {
            return new ResponseEntity<>(HttpStatus.GONE);
        } catch (InconsistentBoardStateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        Player aiPlayer = new RandomPlayer();

        try {
            boardState = boardService.doPlayerMove(aiPlayer.nextMove(boardState), boardState);
            if(boardState.getGameState() != GameState.ACTIVE){
                redisService.upsertValue(boardState.getId().toString(), boardState);
                return ResponseEntity.ok(boardState);
            }
        } catch (InvalidMoveException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (FinishedGameException e) {
            return new ResponseEntity<>(HttpStatus.GONE);
        } catch (InconsistentBoardStateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        redisService.upsertValue(boardState.getId().toString(), boardState);
        return ResponseEntity.ok(boardState);
    }

}
