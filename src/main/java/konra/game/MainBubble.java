package konra.game;

import java.util.Date;

public class MainBubble {

    private static int NEXT_ID = 0;

    private int id;
    private int progress;
    private int max;
    private double multiplier;
    private double currentMulti;
    private double speed;
    private long expires;

    public MainBubble() {
        this.id = NEXT_ID++;
        this.progress = 0;
    }

    public void incrementProgress(){
        this.progress++;

    }

    public static MainBubble next() {

        MainBubble bubble = new MainBubble();
        bubble.max = (int) (Math.random()*100);
        bubble.multiplier = 2 + (int) (Math.random()*5);
        bubble.speed = 1;
        bubble.expires = new Date().getTime() + 600 * bubble.max;
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

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public double getCurrentMulti() {
        return currentMulti;
    }

    public void setCurrentMulti(double currentMulti) {
        this.currentMulti = currentMulti;
    }
}
