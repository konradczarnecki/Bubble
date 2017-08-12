package konra.game;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BubbleGame {

    private MainBubble bubble;
    private ScheduledExecutorService scheduler;

    private List<Bet> bets;

    public BubbleGame() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.bets = new ArrayList<>();
        newBubble();
    }

    @PostConstruct
    public void init(){
    }

    public void update(){

        if(bubble.getProgress() < bubble.getMax())
            bubble.incrementProgress();

        else if (bubble.getProgress() == bubble.getMax()){
            this.bets = new ArrayList<>();
            bubble.incrementProgress();
        }

        else if (bubble.getProgress() < 100)
            bubble.incrementProgress();


        if(bubble.getProgress() == 100){
            newBubble();
        }
    }

    private void newBubble() {

        this.bubble = MainBubble.next();
        long interval = (long) (70 / this.bubble.getSpeed());
        scheduler.scheduleAtFixedRate(this::update, 0, 700, TimeUnit.MILLISECONDS);
    }

    public MainBubble getBubble() {
        return bubble;
    }
}
