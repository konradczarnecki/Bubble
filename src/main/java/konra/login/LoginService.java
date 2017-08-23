package konra.login;

import konra.common.Balance;
import konra.common.DAO;
import konra.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginService {

    DAO dao;

    @Autowired
    public LoginService(DAO dao){
        this.dao = dao;
    }

    @Transactional
    public User authenticateUser(User loggedUser){

        User usr = dao.getUser(loggedUser.getUsername());
        if(usr.getPassword().equals(loggedUser.getPassword())) return usr;
        else return null;
    }

    @Transactional
    public void newUser(String username, String password, String email){

        User user = new User(username, password, email);
        Balance balance = new Balance();
        balance.setValue(1000);
        user.setBalance(balance);
        dao.addUser(user);
    }

    @Transactional
    public void newUser(User user){

        Balance balance = new Balance();
        balance.setValue(1000);
        user.setBalance(balance);
        dao.addUser(user);
    }
}
