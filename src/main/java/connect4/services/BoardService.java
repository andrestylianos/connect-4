package connect4.services;

import connect4.enums.GameState;
import connect4.exceptions.FinishedGameException;
import connect4.exceptions.InconsistentBoardStateException;
import connect4.exceptions.InvalidMoveException;
import connect4.models.BoardSize;
import connect4.models.BoardState;
import connect4.enums.Disc;
import connect4.models.PlayerMove;
import connect4.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class BoardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BoardState initializeBoard(BoardSize boardSize) {

        Disc[][] discs = new Disc[boardSize.getHorizontalSize()][boardSize.getVerticalSize()];

        for (Disc[] discRow : discs) {
            Arrays.fill(discRow, Disc.EMPTY);
        }

        return new BoardState(UUID.randomUUID(), boardSize, discs, Disc.EMPTY, GameState.ACTIVE);
    }

    public BoardState doPlayerMove(PlayerMove move, BoardState boardState) throws InvalidMoveException, FinishedGameException, InconsistentBoardStateException {

        if (!validateMove(move, boardState)) {
            logger.error("An invalid move was tried");
            throw new InvalidMoveException();
        }

        Disc[][] discs = boardState.getDiscs();

        for (int x = 0; x < boardState.getBoardSize().getVerticalSize(); x++) {
            if (discs[move.getColumn()][x] == Disc.EMPTY) {
                discs[move.getColumn()][x] = move.getDisc();
                break;
            }
        }

        return new BoardState(boardState.getId(), boardState.getBoardSize(), discs, move.getDisc(), classifyState(boardState, move.getDisc()));

    }

    public GameState classifyState(BoardState boardState, Disc player) throws InconsistentBoardStateException {

        if (checkWinningGame(boardState.getDiscs())) {
            switch (player) {
                case PLAYER_ONE:
                    return GameState.PLAYER_ONE_WIN;
                case PLAYER_TWO:
                    return GameState.PLAYER_TWO_WIN;
                case EMPTY:
                    throw new InconsistentBoardStateException();
            }
        } else if (checkTiedGame(boardState.getDiscs())) {
            return GameState.TIE;
        }

        return GameState.ACTIVE;
    }

    protected boolean validateMove(PlayerMove move, BoardState boardState) throws FinishedGameException {

        if (boardState.getGameState() != GameState.ACTIVE) {
            logger.error("Trying to play in a finished game");
            throw new FinishedGameException();
        }

        if (move.getDisc() == boardState.getLastPlayer()) {
            return false;
        }

        Disc[] discs = boardState.getDiscs()[move.getColumn()];

        // Check if there is space available in the column
        return Arrays.stream(discs).anyMatch(disc -> disc.equals(Disc.EMPTY));

    }

    protected boolean checkWinningGame(Disc[][] discs) {

        return checkColumnWin(discs) || checkRowWin(discs) || checkFirstDiagWin(discs) || checkSecondDiagWin(discs);

    }

    protected boolean checkTiedGame(Disc[][] discs) {

        return Arrays
                .stream(discs)
                .flatMap(Arrays::stream)
                .noneMatch(disc -> disc.equals(Disc.EMPTY));

    }

    protected boolean checkColumnWin(Disc[][] discs) {

        Pair<Integer, Integer> playerDiscs = new Pair<>(0, 0);

        for (Disc[] discColumn : discs) {

            for (Disc disc : discColumn) {

                playerDiscs = countNewDisc(playerDiscs, disc);
                if (checkDiscCount(playerDiscs)) return true;

            }

            playerDiscs = new Pair<>(0, 0);

        }

        return false;
    }


    protected boolean checkRowWin(Disc[][] discs) {

        Pair<Integer, Integer> playerDiscs = new Pair<>(0, 0);

        for (int y = 0; y < discs[0].length; y++) {

            for (Disc[] discRow : discs) {
                Disc disc = discRow[y];

                playerDiscs = countNewDisc(playerDiscs, disc);
                if (checkDiscCount(playerDiscs)) return true;

            }

            playerDiscs = new Pair<>(0, 0);

        }

        return false;
    }

    protected boolean checkFirstDiagWin(Disc[][] discs) {

        Pair<Integer, Integer> playerDiscs = new Pair<>(0, 0);

        int horizontalSize = discs.length;
        int verticalSize = discs[0].length;

        // Traverse diagonals starting on Y axis
        for (int aux = 0; aux < verticalSize; aux++) {

            int x = 0;
            int y = aux;

            while (x < horizontalSize && y < verticalSize) {

                Disc disc = discs[x][y];
                playerDiscs = countNewDisc(playerDiscs, disc);
                if (checkDiscCount(playerDiscs)) return true;
                x++;
                y++;
            }

            playerDiscs = new Pair<>(0, 0);

        }

        // Traverse diagonals starting on X axis, skipping [0][0] since it'first covered above
        for (int aux = 1; aux < horizontalSize; aux++) {

            int x = aux;
            int y = 0;

            while (x < horizontalSize && y < verticalSize) {

                Disc disc = discs[x][y];
                playerDiscs = countNewDisc(playerDiscs, disc);
                if (checkDiscCount(playerDiscs)) return true;
                x++;
                y++;
            }

            playerDiscs = new Pair<>(0, 0);
        }

        return false;
    }

    protected boolean checkSecondDiagWin(Disc[][] discs) {

        Pair<Integer, Integer> playerDiscs = new Pair<>(0, 0);

        int horizontalSize = discs.length;
        int verticalSize = discs[0].length;

        // Traverse diagonals starting on Y axis
        for (int aux = 0; aux < verticalSize; aux++) {

            int x = horizontalSize - 1;
            int y = aux;

            while (x >= 0 && y < verticalSize) {

                Disc disc = discs[x][y];
                playerDiscs = countNewDisc(playerDiscs, disc);
                if (checkDiscCount(playerDiscs)) return true;
                x--;
                y++;
            }

            playerDiscs = new Pair<>(0, 0);
        }

        // Traverse diagonals starting on X axis, skipping [horizontalSize - 1][0] since it'first covered above
        for (int aux = 0; aux < horizontalSize - 1; aux++) {

            int x = aux;
            int y = 0;

            while (x >= 0 && y < verticalSize) {

                Disc disc = discs[x][y];
                playerDiscs = countNewDisc(playerDiscs, disc);
                if (checkDiscCount(playerDiscs)) return true;
                x--;
                y++;
            }

            playerDiscs = new Pair<>(0, 0);
        }

        return false;
    }

    protected Pair<Integer, Integer> countNewDisc(Pair<Integer, Integer> playerDiscs, Disc disc) {
        switch (disc) {
            case EMPTY:
                playerDiscs = new Pair<>(0, 0);
                break;
            case PLAYER_ONE:
                playerDiscs = new Pair<>(playerDiscs.first + 1, 0);
                break;
            case PLAYER_TWO:
                playerDiscs = new Pair<>(0, playerDiscs.second + 1);
                break;
        }
        return playerDiscs;
    }

    private boolean checkDiscCount(Pair<Integer, Integer> discsCount) {
        return (discsCount.first >= 4) || (discsCount.second >= 4);
    }

}
