package connect4.models;

public class BoardState {

    private final BoardSize boardSize;
    private final Disc[][] discs;
    private final Disc lastPlayer;

    public BoardState(BoardSize boardSize, Disc[][] discs, Disc lastPlayer) {
        this.boardSize = boardSize;
        this.discs = discs;
        this.lastPlayer = lastPlayer;
    }

    public Disc[][] getDiscs() {
        return discs;
    }

    public Disc getLastPlayer() {
        return lastPlayer;
    }
}
