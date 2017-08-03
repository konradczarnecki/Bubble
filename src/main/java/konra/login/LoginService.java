package konra.login;

import konra.common.DAO;
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
    public User getUser(String username){
        return dao.getUser(username);
    }

    @Transactional
    public void newUser(String username, String password, String email){

        User user = new User(username, password, email);
        Balance balance = new Balance();
        balance.setValue(1000);
        user.setBalance(balance);
        dao.addUser(user);
    }
}
