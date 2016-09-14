package connect4.models;

public enum BoardSize {

    SIZE_DEFAULT(7, 6);

    private final int horizontalSize;
    private final int verticalSize;

    BoardSize(int horizontalSize, int verticalSize) {
        this.horizontalSize = horizontalSize;
        this.verticalSize = verticalSize;
    }

    public int getHorizontalSize() {
        return this.horizontalSize;
    }

    public int getVerticalSize() {
        return this.verticalSize;
    }

}
