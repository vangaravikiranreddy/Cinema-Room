package cinema;

public class Seat {
    public int row;
    public int column;
    public int price = 8;
    protected boolean isAvailable = true;

    Seat() {}

    Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    protected boolean isAvailable() {
        return isAvailable;
    }
}