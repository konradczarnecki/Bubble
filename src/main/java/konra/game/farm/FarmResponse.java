package konra.game.farm;

import konra.common.Response;

import java.util.List;

public class FarmResponse extends Response {

    private List<FarmBubble> bubbles;
    private int balance;

    public FarmResponse() {
    }

    public List<FarmBubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(List<FarmBubble> bubbles) {
        this.bubbles = bubbles;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
