package app.Services;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import app.Entities.Reviews;
import app.Util.HibernateUtil;

@Stateless
public class ReviewService {
    public List<Reviews> getReviews(int id){
        Session session = null;
        List<Reviews> review = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "FROM Reviews r WHERE r.course.courseId = :id";
            Query<Reviews> query = session.createQuery(hql, Reviews.class);
            query.setParameter("id", id);
            review = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return review;
    }

    public boolean makeReview(Reviews review){
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.save(review);
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
}
