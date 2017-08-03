package konra.game;

import konra.login.Balance;
import konra.login.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BubbleService {

    private Map<Integer, Bet> bets;
    volatile private Map<String, Bubble> bubbles;
    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void initialize(){

        bubbles = new HashMap<>();
        bets = new HashMap<>();
        scheduler = Executors.newScheduledThreadPool(1);

        Runnable newBubble = () -> {

            if(Math.random() < 0.5) return;

            Bubble bubble;
            boolean overlaps;

            do{
                overlaps = false;
                bubble = Bubble.random();
                for(Bubble b: bubbles.values())
                    if(bubble.overlaps(b)) overlaps = true;
            }while(overlaps);

            bubble.setExpires(System.currentTimeMillis() + bubble.getLifespan() * 1000);
            bubbles.put(bubble.getId(), bubble);
        };

        scheduler.scheduleAtFixedRate(newBubble, 5000, 7000, TimeUnit.MILLISECONDS);
    }

    public List<Bubble> getBubbles(){

        List<Bubble> activeBubbles = new ArrayList<>();
        Map<String, Bubble> activeBubblesMap = new HashMap<>();
        for(Bubble b: bubbles.values())
            if(b.getExpires() > System.currentTimeMillis()){
                activeBubbles.add(b);
                activeBubblesMap.put(b.getId(), b);
            } else {
                b.expire();
            }

        bubbles = activeBubblesMap;
        return activeBubbles;
    }

    public boolean makeBet(User user, int amount, String bubbleId){

        Balance balance = user.getBalance();
        if(balance.getValue() - amount < 0) return false;

        balance.subtract(amount);
        Bet bet = new Bet(user, amount);
        bets.put(bet.getId(), bet);
        bubbles.get(bubbleId).addBet(bet);

        return true;
    }

    public boolean redeem(User user, String bubbleId){

        int reward = 0;
        Bubble bubble = bubbles.get(bubbleId);

        for(Bet bet: bubble.getBets().values()){

            if(bet.getUser().getId() == user.getId() && bet.isValid()){

                double stMultiplier = bubble.getProgressFor(bet.getTimestamp()) * bubble.getMultiplier();
                double enMultiplier = bubble.getProgress() * bubble.getMultiplier();

                double m = enMultiplier / stMultiplier;

                reward += bet.getAmount() * m;
                bet.setValid(false);
            }
        }

        user.getBalance().add(reward);

        return reward > 0;
    }
}
