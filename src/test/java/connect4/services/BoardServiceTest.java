package connect4.services;

import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.models.Disc;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardServiceTest {

    @Test
    public void initializeBoard() throws Exception {

        BoardSize boardSize = BoardSize.SIZE_DEFAULT;
        BoardService boardService = new BoardService();
        BoardState boardState = boardService.initializeBoard(boardSize);
        boolean isBoardEmpty = Arrays.stream(boardState.getDiscs())
                .flatMap(Arrays::stream)
                .allMatch(disc -> disc.equals(Disc.EMPTY));
        assertTrue("Initialized board should be empty but it is not", isBoardEmpty);

    }

}