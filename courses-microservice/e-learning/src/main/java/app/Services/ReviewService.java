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
    public List<Reviews> getReviews(Long id){
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

    public List<Reviews> getReviews(){
        Session session = null;
        List<Reviews> reviews = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "FROM Reviews";
            Query<Reviews> query = session.createQuery(hql, Reviews.class);
            reviews = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return reviews;
    }

    public boolean makeReview(Reviews review){
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            String hql = "FROM Reviews r WHERE r.studentId = :id AND r.course.courseId =:crs";
            Query<Reviews> query = session.createQuery(hql, Reviews.class);
            query.setParameter("id", review.getStudentId());
            query.setParameter("crs", review.getCourse());
            List<Reviews> tmp = query.getResultList();
            if(!tmp.isEmpty() || review.getCourse().getStatus() != app.Util.Enums.Status.DONE)
                return false;
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
