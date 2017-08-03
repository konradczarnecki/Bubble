package konra.game;

import konra.common.GenericResponse;

import java.util.List;

public class StateResponse extends GenericResponse {

    private List<Bubble> bubbles;
    private int balance;

    public StateResponse() {
    }

    public List<Bubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(List<Bubble> bubbles) {
        this.bubbles = bubbles;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
