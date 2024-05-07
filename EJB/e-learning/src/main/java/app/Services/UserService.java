package app.Services;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import app.Models.Users;
import app.Util.HibernateUtil;
import app.Util.Validations.validateEmail;


@Stateless
public class UserService {
    public List<Users> login(String email, String password) {
        Session session = HibernateUtil.getSession();
        String hql = "FROM Users WHERE email = :email and password = :pass";
        Query<Users> query = session.createQuery(hql, Users.class);
        query.setParameter("email", email);
        query.setParameter("pass", password);
        List<Users> resultList = query.getResultList();
        return resultList;
    }

    public boolean signup(Users wrapper){
        Transaction transaction = null;
        try {
            if(login(wrapper.getEmail(), wrapper.getPassword()).size() > 0 || !validateEmail.validate(wrapper.getEmail()))
                return false;
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.save(wrapper);
            transaction.commit();
            HibernateUtil.closeSession(session);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Users getUser(Long id) {
        Session session = null;
        Users user = null;
        try {
            session = HibernateUtil.getSession();
            user = session.get(Users.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return user;
    }

    public List<Users> getUsers(){
        Session session = HibernateUtil.getSession();
        return session.createQuery("FROM Users", Users.class).getResultList();
    }
}
