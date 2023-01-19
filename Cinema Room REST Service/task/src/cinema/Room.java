package cinema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

public class Room {

    public int totalRows = 9;
    public int totalColumns = 9;
    public List<Seat> availableSeats = new ArrayList<>();

    Room() {
        for (int i = 1; i <= totalRows; i++) {
            for (int k = 1; k <= totalColumns; k++) {
                Seat seat = new Seat(i, k);
                if (i <= 4) seat.setPrice(10);
                availableSeats.add(seat);
            }
        }
    }

    public Seat getSeat(int row, int column) {
        for (Seat seat : availableSeats)
            if (seat.getRow() == row && seat.getColumn() == column)
                return seat;
        throw new NullPointerException();
    }
}