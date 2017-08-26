package konra.game;

import konra.common.Balance;
import konra.common.Response;

import java.util.List;

public class StateResponse extends Response {

    private MainBubble bubble;
    private Balance balance;
    private List<Bet> bets;

    public StateResponse() {}

    public StateResponse(String status){
        super(status);
    }

    public MainBubble getBubble() {
        return bubble;
    }

    public void setBubble(MainBubble bubble) {
        this.bubble = bubble;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
}
