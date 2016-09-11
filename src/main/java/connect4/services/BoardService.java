package connect4.services;

import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.models.Disc;
import connect4.models.PlayerMove;

import java.util.Arrays;

public class BoardService {

    public BoardState initializeBoard(BoardSize boardSize){

        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];

        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        return new BoardState(boardSize, discs, Disc.EMPTY);
    }

    public BoardState doPlayerMove(PlayerMove move, BoardState boardState) {
        return null;
    }

    protected boolean validateMove(PlayerMove move, BoardState boardState) {

        if (move.getDisc() == boardState.getLastPlayer()) {
            return false;
        }

        Disc[] discs = boardState.getDiscs()[move.getColumn()];

        // Check if there is space available in the column
        return Arrays.stream(discs).anyMatch(disc -> disc.equals(Disc.EMPTY));

    }

}