package connect4.services;

import connect4.enums.GameState;
import connect4.exceptions.FinishedGameException;
import connect4.exceptions.InvalidMoveException;
import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.enums.Disc;
import connect4.models.PlayerMove;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BoardService {

    public BoardState initializeBoard(BoardSize boardSize) {

        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];

        for (Disc[] discRow : discs) {
            Arrays.fill(discRow, Disc.EMPTY);
        }

        return new BoardState(boardSize, discs, Disc.EMPTY, GameState.ACTIVE);
    }

    public BoardState doPlayerMove(PlayerMove move, BoardState boardState) throws InvalidMoveException, FinishedGameException {

        if (!validateMove(move, boardState)) {
            throw new InvalidMoveException();
        }

        Disc[][] discs = boardState.getDiscs();

        for (int x = 0; x < boardState.getBoardSize().getVerticalSize(); x++) {
            if (discs[move.getColumn()][x] == Disc.EMPTY) {
                discs[move.getColumn()][x] = move.getDisc();
                break;
            }
        }

        return new BoardState(boardState.getBoardSize(), discs, move.getDisc(), classifyState(boardState));

    }

    public GameState classifyState(BoardState boardState) {

        if (checkActiveGame(boardState.getDiscs())) {
            return GameState.ACTIVE;
        }


        return GameState.ACTIVE;
    }

    protected boolean validateMove(PlayerMove move, BoardState boardState) throws FinishedGameException {

        if (boardState.getGameState() != GameState.ACTIVE) {
            throw new FinishedGameException();
        }

        if (move.getDisc() == boardState.getLastPlayer()) {
            return false;
        }

        Disc[] discs = boardState.getDiscs()[move.getColumn()];

        // Check if there is space available in the column
        return Arrays.stream(discs).anyMatch(disc -> disc.equals(Disc.EMPTY));

    }

    protected boolean checkActiveGame(Disc[][] discs) {
        return Arrays
                .stream(discs)
                .flatMap(Arrays::stream)
                .anyMatch(disc -> disc.equals(Disc.EMPTY));
    }

    protected boolean checkFinishedGame(Disc[][] discs) {

        if (checkColumnWin(discs) || checkRowWin(discs)) {
            return true;
        }

        return false;
    }

    protected boolean checkColumnWin(Disc[][] discs) {
        int playerOneCount = 0;
        int playerTwoCount = 0;

        for (Disc[] discColumn : discs) {

            for (Disc disc : discColumn) {

                switch (disc) {
                    case EMPTY:
                        playerOneCount = 0;
                        playerTwoCount = 0;
                        break;
                    case PLAYER_ONE:
                        playerOneCount++;
                        playerTwoCount = 0;
                        break;
                    case PLAYER_TWO:
                        playerOneCount = 0;
                        playerTwoCount++;
                        break;
                }
                if ((playerOneCount >= 4) || (playerTwoCount >= 4)) {
                    return true;
                }

            }

            playerOneCount = 0;
            playerTwoCount = 0;

        }

        return false;
    }

    protected boolean checkRowWin(Disc[][] discs) {
        int playerOneCount = 0;
        int playerTwoCount = 0;

        for (int y = 0; y < discs[0].length; y++) {

            for (Disc[] discRow : discs) {
                Disc disc = discRow[y];

                switch (disc) {
                    case EMPTY:
                        playerOneCount = 0;
                        playerTwoCount = 0;
                        break;
                    case PLAYER_ONE:
                        playerOneCount++;
                        playerTwoCount = 0;
                        break;
                    case PLAYER_TWO:
                        playerOneCount = 0;
                        playerTwoCount++;
                        break;
                }

                if ((playerOneCount >= 4) || (playerTwoCount >= 4)) {
                    return true;
                }

            }

            playerOneCount = 0;
            playerTwoCount = 0;

        }

        return false;
    }

    protected boolean checkFirstDiagWin(Disc[][] discs) {
        int playerOneCount = 0;
        int playerTwoCount = 0;

        int horizontalSize = discs.length;
        int verticalSize = discs[0].length;

        for (int x = 0; x < horizontalSize + verticalSize - 1; x++) {

            for (int y = 0; y < discs[0].length; y++) {
                Disc disc = discs[x][y];

                switch (disc) {
                    case EMPTY:
                        playerOneCount = 0;
                        playerTwoCount = 0;
                        break;
                    case PLAYER_ONE:
                        playerOneCount++;
                        playerTwoCount = 0;
                        break;
                    case PLAYER_TWO:
                        playerOneCount = 0;
                        playerTwoCount++;
                        break;
                }

                if ((playerOneCount >= 4) || (playerTwoCount >= 4)) {
                    return true;
                }

            }

            playerOneCount = 0;
            playerTwoCount = 0;

        }

        return false;
    }



}
