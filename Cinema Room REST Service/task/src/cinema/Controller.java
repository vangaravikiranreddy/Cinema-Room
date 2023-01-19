package cinema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Ref;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class Controller {
    private final Room room = new Room();
    HashMap<UUID, Seat> storeTokens = new HashMap<>();
    private int total_income = 0;
    private int available_seats = 81;
    private int tickets_sold = 0;
    private int lastTicket = 0;

    @GetMapping("/seats")
    public Room getAvailableSeats() throws JsonProcessingException {
        return room;
    }

    @PostMapping("/purchase")
    public Purchase bookSeat(@RequestBody Seat request) throws JsonProcessingException {

        if (request.row < 1 || request.column < 1 || request.row > 9 || request.column > 9)
            throw new SeatException("The number of a row or a column is out of bounds!");

        Seat seat = room.getSeat(request.getRow(), request.getColumn());

        if (seat.isAvailable()) {
            seat.setAvailable(false);
            Purchase r = new Purchase(UUID.randomUUID());
            r.setTicket(seat);

            lastTicket = r.getTicket().getPrice();
            System.out.println(lastTicket);
            total_income += lastTicket;
            available_seats -= 1;
            tickets_sold += 1;

            storeTokens.put(r.getToken(), r.getTicket());
            return r;
        }
        else throw new SeatException("The ticket has been already purchased!");
    }

    @PostMapping("/return")
    public Refund getRefund(@RequestBody Purchase token) throws JsonProcessingException {
       if (!storeTokens.containsKey(token.getToken())) {
           throw new SeatException("Wrong token!");
       }
       Refund ticket = new Refund(token.getToken(), storeTokens);
       total_income -= storeTokens.get(token.getToken()).getPrice();
       available_seats += 1;
       tickets_sold -= 1;
       return ticket;
    }

    @PostMapping("/stats")
    public Statistics sendStats(@RequestParam(value = "password", required = false) String password) {
        if(password == null) {
            throw new StatsException("The password is wrong!");
        }
        else if(password.equals("super_secret")) {
           Statistics stat = new Statistics();

           stat.setCurrent_income(total_income);
           stat.setNumber_of_available_seats(available_seats);
           stat.setNumber_of_purchased_tickets(tickets_sold);

          stat.getCurrent_income();
          stat.getNumber_of_available_seats();
          stat.getNumber_of_purchased_tickets();

           return stat;
        }
        else throw new StatsException("The password is wrong!");
    }

    @ExceptionHandler(SeatException.class)
    public ResponseEntity<ErrorMessage> handleBadRequest(SeatException e) {
        ErrorMessage body = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StatsException.class)
    public ResponseEntity<ErrorMessage> handleUnauthorizedRequest(StatsException e) {
        ErrorMessage body = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}
