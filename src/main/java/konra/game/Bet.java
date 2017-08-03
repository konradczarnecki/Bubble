package konra.game;

import konra.login.User;

import java.util.Date;

public class Bet {

    static int nextId = 0;

    int id;
    private User user;
    private int amount;
    private long timestamp;
    private boolean valid;

    public Bet(User user, int amount) {
        this.valid = true;
        this.timestamp = new Date().getTime();
        this.id = nextId++;
        this.user = user;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
