package konra.game;

import konra.common.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class GameService {

    private static final Logger log = Logger.getLogger(GameService.class);

    private ScheduledExecutorService bubbleScheduler;
    private ScheduledFuture bubbleCallback;
    private MainBubble bubble;
    private List<Bet> bets;

    @PostConstruct
    public void init(){

        this.bubbleScheduler = Executors.newScheduledThreadPool(0);
        this.bets = new ArrayList<>();
        newBubble();
    }

    public void update(){

        logProgress();
        if(bubble.getProgress() < bubble.getMax()) bubble.incrementProgress();
        else newBubble();
    }

    private void newBubble() {

        this.bubble = MainBubble.next();
        this.bets = new ArrayList<>();

        long interval = (long) (600 / this.bubble.getSpeed());
        if(bubbleCallback != null) bubbleCallback.cancel(false);
        bubbleCallback = bubbleScheduler.scheduleAtFixedRate(this::update, 4000, interval, TimeUnit.MILLISECONDS);
    }

    public boolean makeBet(User user, int amount){

        if(user.getBalance().getValue() < amount) return false;

        Bet b = new Bet(user, amount);
        b.setBase(bubble.getProgress());
        bets.add(b);
        return true;
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
}
