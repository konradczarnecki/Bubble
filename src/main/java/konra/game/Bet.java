package konra.game;

import konra.common.User;

import java.util.Date;

public class Bet {

    private static int nextId = 0;

    private int id;
    private User user;
    private int amount;
    private long timestamp;
    private int base;
    private int prize;

    public Bet(User user, int amount) {
        this.timestamp = new Date().getTime();
        this.id = nextId++;
        this.user = user;
        this.amount = amount;
    }

    public boolean equals(Object o){
        if(!(o instanceof Bet)) return false;
        Bet b = (Bet) o;
        return b.id == this.id;
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

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }
}
