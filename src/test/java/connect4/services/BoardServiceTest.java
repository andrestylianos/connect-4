package connect4.services;

import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.models.Disc;
import connect4.models.PlayerMove;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardServiceTest {

    @Test
    public void shouldInitializeBoard() throws Exception {

        BoardSize boardSize = BoardSize.SIZE_DEFAULT;
        BoardService boardService = new BoardService();
        BoardState boardState = boardService.initializeBoard(boardSize);
        boolean isBoardEmpty = Arrays.stream(boardState.getDiscs())
                .flatMap(Arrays::stream)
                .allMatch(disc -> disc.equals(Disc.EMPTY));
        assertTrue("Initialized board should be empty but it is not", isBoardEmpty);

        assertEquals("None of the players should have played yet", Disc.EMPTY, boardState.getLastPlayer());

    }

    @Test
    public void shouldFailOnInvalidMove() throws Exception {

        BoardSize boardSize = BoardSize.SIZE_DEFAULT;
        BoardService boardService = new BoardService();
        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        Arrays.fill(discs[0],Disc.PLAYER_TWO);
        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_TWO);

        assertFalse("Player should not be able to add disc to a full column", boardService.validateMove(move, boardState));
    }

    @Test
    public void shouldFailIfPlayingTwoTimes() throws Exception {

        BoardSize boardSize = BoardSize.SIZE_DEFAULT;
        BoardService boardService = new BoardService();
        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_ONE);

        assertFalse("Player should not be able to play two times in a row", boardService.validateMove(move, boardState));
    }

    @Test
    public void shouldAllowValidMove() throws Exception {

        BoardSize boardSize = BoardSize.SIZE_DEFAULT;
        BoardService boardService = new BoardService();
        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];

        // Leave the column with one spot left
        Arrays.fill(discs[0],Disc.PLAYER_TWO);
        discs[0][boardSize.getVerticalSize()-1] = Disc.EMPTY;

        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_TWO);

        assertTrue("Player should be able to make a move that is valid", boardService.validateMove(move, boardState));
    }
}