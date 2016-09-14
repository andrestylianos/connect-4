package connect4.models;

import connect4.enums.Disc;
import connect4.enums.GameState;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

public class BoardState implements Serializable {

    private UUID id;
    private BoardSize boardSize;
    private Disc[][] discs;
    private Disc lastPlayer;
    private GameState gameState;

    public BoardState(UUID id, BoardSize boardSize, Disc[][] discs, Disc lastPlayer, GameState gameState) {
        this.id = id;
        this.boardSize = boardSize;
        this.discs = discs;
        this.lastPlayer = lastPlayer;
        this.gameState = gameState;
    }

    public UUID getId() {
        return id;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBoardSize(BoardSize boardSize) {
        this.boardSize = boardSize;
    }

    public void setDiscs(Disc[][] discs) {
        this.discs = discs;
    }

    public void setLastPlayer(Disc lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof BoardState)) {
            return false;
        }
        BoardState boardState = (BoardState) obj;

        if (this.boardSize != boardState.getBoardSize()) {
            return false;
        }

        if (!Arrays.deepEquals(this.discs, boardState.getDiscs())) {
            return false;
        }

        if (this.lastPlayer != boardState.getLastPlayer()) {
            return false;
        }

        if (this.gameState != boardState.gameState) {
            return false;
        }

        return true;
    }
}
