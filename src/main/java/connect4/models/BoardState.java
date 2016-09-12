package connect4.models;

import connect4.enums.Disc;
import connect4.enums.GameState;

public class BoardState {

    private final BoardSize boardSize;
    private final Disc[][] discs;
    private final Disc lastPlayer;
    private final GameState gameState;

    public BoardState(BoardSize boardSize, Disc[][] discs, Disc lastPlayer, GameState gameState) {
        this.boardSize = boardSize;
        this.discs = discs;
        this.lastPlayer = lastPlayer;
        this.gameState = gameState;
    }

    public Disc[][] getDiscs() {
        return discs;
    }

    public Disc getLastPlayer() {
        return lastPlayer;
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public GameState getGameState() {
        return gameState;
    }
}
