package connect4.services;

import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.models.Disc;

import java.util.Arrays;

public class BoardService {

    public BoardState initializeBoard(BoardSize boardSize){

        Disc[][] discs = new Disc[boardSize.getColumnSize()][boardSize.getRowSize()];

        for(Disc[] ps: discs){
            Arrays.fill(ps, Disc.EMPTY);
        }

        return new BoardState(boardSize, discs);
    }

}
