package konra.common;

import konra.common.Balance;
import konra.game.Bet;

import javax.persistence.*;

@Entity
@Table(name = "login")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "pwd")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String profileImg;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Balance balance;

    public User(){}

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public boolean equals(Object o){
        if(!(o instanceof User)) return false;
        User b = (User) o;
        return b.id == this.id;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
