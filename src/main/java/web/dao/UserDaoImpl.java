package web.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import java.util.Iterator;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory, Session session) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public User getUserByName(String name) {
        Session session = currentSession();

        Query query = session.createQuery("from User where name = :name")
                .setParameter("name", name)
                .setMaxResults(1);

        List<User> users = query.list();
        Iterator<User> it = users.iterator();
        if (!it.hasNext()) {
            return null;
        }
        User user = it.next();
        return user;
    }

    @Override
    public User getUserById(Long id) {
        //Session session = currentSession();
        //session.clear();
        //User user = session.load(User.class, id);
        //return user;

        Session session = currentSession();

        Query query = session.createQuery("from User where id = :id")
                .setParameter("id", id)
                .setMaxResults(1);

        List<User> users = query.list();
        Iterator<User> it = users.iterator();
        if (!it.hasNext()) {
            return null;
        }
        User user = it.next();
        return user;
    }


    @Override
    public void addUser (User user) {
        Session session = currentSession();
        session.save(user);
        session.flush();
    }

    @Override
    public void updateUser(User user) {
        Session session = currentSession();
        session.update(user);

    }

    @Override
    public void removeUser(Long id) {
        Session session = currentSession();
        User user = session.load(User.class, id);

        if(user !=null){
            session.delete(user);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Session session = currentSession();
        List<User> userList = session.createQuery("from User").list();
        return userList;
    }


    public Role getRoleByName(String name) {

        Query query = currentSession().createQuery("from Role where role = :role")
                .setParameter("role", name)
                .setMaxResults(1);


        Role role = (Role) query.uniqueResult();

        return role;
    }

    @Override
    public Role getRoleById(Long id) {
        return currentSession().load(Role.class, id);
    }

    @Override
    public void addRole(Role role) {
        currentSession().save(role);
    }


}
