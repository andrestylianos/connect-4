package connect4.models;

public class BoardState {

    private final BoardSize boardSize;
    private final Disc[][] discs;

    public BoardState(BoardSize boardSize, Disc[][] discs) {
        this.boardSize = boardSize;
        this.discs = discs;
    }

    public Disc[][] getDiscs() {
        return discs;
    }
}
