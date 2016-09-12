package connect4.models;

import connect4.enums.Disc;

public final class PlayerMove {

    private final Disc disc;
    private final int column;

    public PlayerMove(Disc disc, int column) {
        this.disc = disc;
        this.column = column;
    }

    public Disc getDisc() {
        return disc;
    }

    public int getColumn() {
        return column;
    }
}
