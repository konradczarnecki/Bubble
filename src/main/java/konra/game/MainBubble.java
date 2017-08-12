package konra.game;

import java.util.Date;

public class MainBubble {

    private static int NEXT_ID;

    private int id;
    private int progress;
    private int max;
    private int multiplier;
    private double speed;
    private long stamp;

    public MainBubble() {
        this.id = NEXT_ID++;
        this.progress = 0;
        this.stamp = new Date().getTime();
    }

    public void incrementProgress(){
        this.progress++;
    }

    public static MainBubble next() {

        MainBubble bubble = new MainBubble();
        bubble.max = (int) (Math.random()*100);
        bubble.multiplier = 2 + (int) (Math.random()*5);
        bubble.speed = 1;
        return bubble;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

}
