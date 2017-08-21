package konra.game;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BubbleGame {

    private ScheduledExecutorService scheduler;
    private MainBubble bubble;
    private List<Bet> bets;

    @PostConstruct
    public void init(){
        this.scheduler = Executors.newScheduledThreadPool(0);
        this.bets = new ArrayList<>();
        newBubble();
    }

    public void update(){

        System.out.println(new Date().getTime() + " - " + bubble.getProgress());

        if(bubble.getProgress() < bubble.getMax())
            bubble.incrementProgress();

        else if (bubble.getProgress() == bubble.getMax()){
            this.bets = new ArrayList<>();
            bubble.incrementProgress();
            newBubble();
        }

        else if (bubble.getProgress() < 100)
            bubble.incrementProgress();


        if(bubble.getProgress() == 100)
            newBubble();

    }

    private void newBubble() {

        this.bubble = MainBubble.next();
        long interval = (long) (600 / this.bubble.getSpeed());

        scheduler.shutdownNow();
        scheduler = Executors.newScheduledThreadPool(0);
        scheduler.scheduleAtFixedRate(this::update, 0, interval, TimeUnit.MILLISECONDS);
    }

    public MainBubble getBubble() {
        return bubble;
    }
}
