package connect4.enums;

public enum Disc {

    EMPTY(0), PLAYER_ONE(1), PLAYER_TWO(2);

    private final int id;

    Disc(int id) {
        this.id = id;
    }
}
