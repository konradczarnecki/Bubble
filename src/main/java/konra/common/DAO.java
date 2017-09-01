package konra.common;

import konra.exchange.Item;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DAO {

    SessionFactory factory;

    @Autowired
    public DAO(SessionFactory factory){
        this.factory = factory;
    }

    public User getUser(String username){

        Query<User> q = factory.getCurrentSession().createQuery("from User where username=:name", User.class);
        q.setParameter("name", username);

        return q.uniqueResult();
    }

    public void addUser(User user){

        factory.getCurrentSession().save(user);
    }

    public List<Item> getExchangeItems() {

        Query<Item> q = factory.getCurrentSession().createQuery("from Item", Item.class);
        return q.getResultList();
    }

    public Item getExchangeItemById(int id){
        return factory.getCurrentSession().get(Item.class, id);
    }
}
