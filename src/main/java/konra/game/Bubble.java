package konra.game;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bubble {

    static int nextId = 0;

    private String id;
    private double size;
    private int lifespan;
    private double multiplier;
    private long expires;
    private int x;
    private int y;
    private double progress;

    private Map<Integer, Bet> bets;

    public Bubble(double size, int lifespan, double multiplier) {
        this.size = size;
        this.lifespan = lifespan;
        this.multiplier = multiplier;
        this.id = "bubble" + nextId++;
        this.bets = new HashMap<>();
    }

    public static Bubble random(){

        //double size = 0.1 + Math.random() / 10;
        double size = 1;
        int lifespan = 5 + (int) (Math.random()*55);
        double multiplier = 1 + Math.random()*4 + 6*(size * lifespan / 100);
        multiplier = Math.round(multiplier * 100) / 100;

        Bubble b = new Bubble(size, lifespan, multiplier);
        b.x = 1 + (int) (Math.random()*10);
        b.y = 1 + (int) (Math.random()*10);

        return b;
    }

    public boolean overlaps(Bubble otherBubble){

        double distance = Math.sqrt(Math.pow(Math.abs(this.x - otherBubble.x), 2)
                + Math.pow(Math.abs(this.y - otherBubble.y), 2));

        return distance < 1.3 * (this.size + otherBubble.size);
    }

    public void expire(){
        for(Bet b: bets.values()){
            b.setValid(false);
        }
    }

    public void addBet(Bet bet){
        this.bets.put(bet.getId(), bet);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getProgress() {

        long now = new Date().getTime();
        long start = expires - lifespan * 1000;

        progress = (now - start) / (expires - start);
        return progress;
    }

    public double getProgressFor(long timestamp){

        long start = expires - lifespan * 1000;

        progress = (timestamp - start) / (expires - start);
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public Map<Integer, Bet> getBets() {
        return bets;
    }

    public void setBets(Map<Integer, Bet> bets) {
        this.bets = bets;
    }
}
