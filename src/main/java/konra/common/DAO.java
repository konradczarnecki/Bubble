package konra.common;

import konra.login.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DAO {

    SessionFactory factory;

    @Autowired
    public DAO(SessionFactory factory){
        this.factory = factory;
    }

    public User getUser(String username){

        Query q = factory.getCurrentSession().createQuery("from User where username=:name");
        q.setParameter("name", username);

        return (User) q.uniqueResult();
    }

    public void addUser(User user){

        factory.getCurrentSession().save(user);
    }
}
