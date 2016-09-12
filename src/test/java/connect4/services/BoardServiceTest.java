package connect4.services;

import connect4.conf.ConnectFourConfiguration;
import connect4.enums.GameState;
import connect4.exceptions.FinishedGameException;
import connect4.exceptions.InvalidMoveException;
import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.enums.Disc;
import connect4.models.PlayerMove;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectFourConfiguration.class)
public class BoardServiceTest {

    private static BoardSize boardSize;

    @Autowired
    private BoardService boardService;

    @BeforeClass
    public static void setUp() throws Exception {
        boardSize = BoardSize.SIZE_DEFAULT;
    }

    @Test
    public void shouldInitializeBoard() throws Exception {

        BoardState boardState = boardService.initializeBoard(boardSize);
        boolean isBoardEmpty = Arrays.stream(boardState.getDiscs())
                .flatMap(Arrays::stream)
                .allMatch(disc -> disc.equals(Disc.EMPTY));
        assertTrue("Initialized board should be empty but it is not", isBoardEmpty);

        assertEquals("None of the players should have played yet", Disc.EMPTY, boardState.getLastPlayer());

    }

    @Test
    public void shouldFailOnInvalidMove() throws Exception {

        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        Arrays.fill(discs[0],Disc.PLAYER_TWO);
        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_TWO, GameState.ACTIVE);

        assertFalse("Player should not be able to add disc to a full column", boardService.validateMove(move, boardState));
    }

    @Test
    public void shouldFailIfPlayingTwoTimes() throws Exception {

        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_ONE, GameState.ACTIVE);

        assertFalse("Player should not be able to play two times in a row", boardService.validateMove(move, boardState));
    }

    @Test(expected = FinishedGameException.class)
    public void validateShouldThrowExceptionIfGameFinished() throws Exception {

        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_ONE, GameState.PLAYER_ONE_WIN);
        boardService.validateMove(move, boardState);
    }

    @Test
    public void shouldAllowValidMove() throws Exception {

        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];

        // Leave the column with one spot left
        Arrays.fill(discs[0],Disc.PLAYER_TWO);
        discs[0][boardSize.getVerticalSize()-1] = Disc.EMPTY;

        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_TWO, GameState.ACTIVE);

        assertTrue("Player should be able to make a move that is valid", boardService.validateMove(move, boardState));
    }

    @Test(expected = InvalidMoveException.class)
    public void doPlayerMoveShouldThrowExceptionOnInvalidMove() throws Exception{

        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        Arrays.fill(discs[0],Disc.PLAYER_TWO);
        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_TWO, GameState.ACTIVE);
        boardService.doPlayerMove(move, boardState);
    }

    @Test
    public void shouldReturnNextStateOnValidMove() throws Exception {
        PlayerMove move = new PlayerMove(Disc.PLAYER_ONE, 0);
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];

        // Leave the column with one spot left
        Arrays.fill(discs[0],Disc.PLAYER_TWO);
        discs[0][boardSize.getVerticalSize()-1] = Disc.EMPTY;

        BoardState boardState = new BoardState(boardSize, discs, Disc.PLAYER_TWO, GameState.ACTIVE);

        BoardService boardServiceSpy = spy(boardService);
        doReturn(GameState.ACTIVE).when(boardServiceSpy).classifyState(any(BoardState.class));

        BoardState nextState = boardServiceSpy.doPlayerMove(move, boardState);

        assertEquals("Service should update the board after applying the move", Disc.PLAYER_ONE, nextState.getDiscs()[0][boardSize.getVerticalSize()-1]);
        assertEquals("Service should update the last player", Disc.PLAYER_ONE, nextState.getLastPlayer());
    }

    @Test
    public void checkFinishedGameShouldReturnFalseIfActive() throws Exception {
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        assertFalse("An empty board should not be classified as finished", boardService.checkFinishedGame(discs));
    }

    @Test
    public void checkColumnWinShouldReturnTrueIfWin() throws Exception {
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        IntStream.range(2,6).forEach(i -> discs[2][i] = Disc.PLAYER_ONE);

        assertTrue("A winning board should be checked as a win", boardService.checkColumnWin(discs));
    }

    @Test
    public void checkColumnWinShouldReturnFalseIfActive() throws Exception {
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        IntStream.range(2,5).forEach(i -> discs[2][i] = Disc.PLAYER_ONE);

        assertFalse("An unfinished board should not be checked as a win", boardService.checkColumnWin(discs));
    }

    @Test
    public void checkRowWinShouldReturnTrueIfWin() throws Exception {
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        IntStream.range(2,6).forEach(i -> discs[i][3] = Disc.PLAYER_ONE);

        assertTrue("A winning board should be checked as a win", boardService.checkRowWin(discs));
    }

    @Test
    public void checkRowWinShouldReturnFalseIfActive() throws Exception {
        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];
        for(Disc[] discRow: discs){
            Arrays.fill(discRow, Disc.EMPTY);
        }

        IntStream.range(2,5).forEach(i -> discs[i][3] = Disc.PLAYER_ONE);

        assertFalse("An unfinished board should not be checked as a win", boardService.checkRowWin(discs));
    }

}