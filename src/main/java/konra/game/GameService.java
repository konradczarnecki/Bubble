package konra.game;

import konra.common.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class GameService {

    private static final Logger log = Logger.getLogger(GameService.class);

    public static final long BUBBLE_DELAY = 3000;

    private ScheduledExecutorService bubbleScheduler;
    private ScheduledFuture bubbleCallback;
    private MainBubble bubble;
    private List<Bet> bets;
    private List<User> redeemed;

    @PostConstruct
    public void init(){

        this.bubbleScheduler = Executors.newScheduledThreadPool(0);
        newBubble();
    }

    private void update(){

        logProgress();
        if(bubble.getProgress() < bubble.getMax()) bubble.incrementProgress();
        else newBubble();
    }

    private void newBubble() {

        this.bubble = MainBubble.next();
        this.bets = new ArrayList<>();
        this.redeemed = new ArrayList<>();

        long interval = (long) (600 / this.bubble.getSpeed());
        if(bubbleCallback != null) bubbleCallback.cancel(false);
        bubbleCallback = bubbleScheduler.scheduleAtFixedRate(this::update, BUBBLE_DELAY, interval, TimeUnit.MILLISECONDS);
    }

    public boolean makeBet(User user, int amount){

        if(user == null || redeemed.contains(user) || user.getBalance().getValue() < amount) return false;

        user.getBalance().subtract(amount);

        Bet b = new Bet(user, amount);
        b.setBase(bubble.getProgress());
        bets.add(b);
        return true;
    }

    public Integer redeem(User user, int bubId) {

        if(user == null || redeemed.contains(user) || bubId != bubble.getId()) return -1;

        int total = 0;
        for(Bet b : bets) if(b.getUser().equals(user)) total += cashBet(b, bubble.getProgress());

        redeemed.add(user);
        return total;
    }

    private Integer cashBet(Bet b, int progress) {

        double start = b.getBase() <= 20 ? 1 : 1 + (b.getBase() - 20) * bubble.getMultiplier() / 80;
        double end = 1 + (progress - 20) * bubble.getMultiplier() / 80;
        int prize = (int) (b.getAmount() * end / start);

        log.info(start + " " + end + " " + prize);

        User user = b.getUser();
        user.getBalance().add(prize);
        return prize;
    }

    private void logProgress(){
        if(bubble.getProgress() % 10 == 0)
            log.info("id: "  + bubble.getId() + " max: " + bubble.getMax() + " - " + bubble.getProgress());
    }

    public MainBubble getBubble() {
        return bubble;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public List<User> getRedeemed(){
        return redeemed;
    }
}
