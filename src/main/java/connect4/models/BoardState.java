package connect4.models;

import connect4.enums.Disc;
import connect4.enums.GameState;

import java.io.Serializable;
import java.util.Arrays;

public class BoardState implements Serializable{

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

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof BoardState)){
            return false;
        }
        BoardState boardState = (BoardState) obj;

        if(this.boardSize != boardState.getBoardSize()){
            return false;
        }

        if(!Arrays.deepEquals(this.discs, boardState.getDiscs())){
            return false;
        }

        if(this.lastPlayer != boardState.getLastPlayer()){
            return false;
        }

        if(this.gameState != boardState.gameState) {
            return false;
        }

        return true;
    }
}
