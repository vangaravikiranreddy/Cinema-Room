package cinema;

import java.util.HashMap;
import java.util.UUID;

public class Refund {
    private Seat returned_ticket;

    public Refund(UUID token, HashMap<UUID, Seat> storeTokens) {
        this.returned_ticket = storeTokens.get(token);
    }

    public Seat getReturned_ticket() {
        return returned_ticket;
    }
}
