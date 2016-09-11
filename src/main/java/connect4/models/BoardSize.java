package connect4.models;

public enum BoardSize {

    SIZE_DEFAULT(7,6);

    private final int columnSize;
    private final int rowSize;

    BoardSize(int columnSize, int rowSize) {
        this.columnSize = columnSize;
        this.rowSize = rowSize;
    }

    public int getColumnSize(){
        return this.columnSize;
    }

    public int getRowSize(){
        return this.rowSize;
    }

}
