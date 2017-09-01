package konra.common;

import javax.persistence.*;


@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @Column(name = "amount")
    private int value;

    @Column(name = "small_h")
    private int smallHints;

    @Column(name = "med_h")
    private int mediumHints;

    @Column(name = "big_h")
    private int bigHints;

    public Balance(){}

    public void subtract(int amount){
        this.value = this.value - amount;
    }

    public void add(int amount){
        this.value = this.value + amount;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public int getSmallHints() {
        return smallHints;
    }

    public void setSmallHints(int smallHints) {
        this.smallHints = smallHints;
    }

    public int getMediumHints() {
        return mediumHints;
    }

    public void setMediumHints(int mediumHints) {
        this.mediumHints = mediumHints;
    }

    public int getBigHints() {
        return bigHints;
    }

    public void setBigHints(int bigHints) {
        this.bigHints = bigHints;
    }

    public void setHintValue(int id, int delta){
        if(id == 1) this.bigHints += delta;
        else if(id == 2) this.mediumHints += delta;
        else if(id == 3) this.bigHints += delta;
    }
}
