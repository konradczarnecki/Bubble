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

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from User where username=:name");
        q.setParameter("name", username);

        User user = (User) q.uniqueResult();
        tx.commit();

        return user;
    }

    public void addUser(User user){

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }
}
