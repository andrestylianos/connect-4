package connect4.ai;

import connect4.models.BoardState;
import connect4.models.PlayerMove;

public interface Player {

    public PlayerMove nextMove(BoardState boardState);

}
