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

}
