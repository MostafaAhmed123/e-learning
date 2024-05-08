package app.Services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Transaction;

import org.hibernate.Session;
import org.hibernate.query.Query;

import app.Models.Courses;
import app.Models.Reviews;
import app.Util.HibernateUtil;

@Stateless
public class CourseService {
    public Courses getCourse(Long id) {
        Session session = null;
        Courses course = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "FROM Courses WHERE courseId = :id";
            Query<Courses> query = session.createQuery(hql, Courses.class);
            query.setParameter("id", id);
            course = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return course != null && course.getApprovedByAdmin() ? course : null;
    }

    private boolean isCourseExist(String name) {
        Session session = HibernateUtil.getSession();
        String hql = "FROM Courses c WHERE name = :tmp AND c.approvedByAdmin = true";
        Query<Courses> query = session.createQuery(hql, Courses.class);
        query.setParameter("tmp", name);
        return query.getResultList().size() > 0;
    }

    public boolean createCourse(Courses course) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            if (isCourseExist(course.getName()))
                return false;
            session.save(course);
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

    public List<Courses> search(String searchTerm, boolean byName) {
        Session session = null;
        List<Courses> courses = null;
        try {
            session = HibernateUtil.getSession();
            String hql;
            Query<Courses> query;
            if (byName) {
                hql = "FROM Courses c WHERE c.name LIKE :searchTerm AND c.approvedByAdmin = true";
                query = session.createQuery(hql, Courses.class);
                query.setParameter("searchTerm", "%" + searchTerm + "%");
            } else {
                hql = "FROM Courses c WHERE c.category LIKE :searchTerm AND c.approvedByAdmin = true";
                query = session.createQuery(hql, Courses.class);
                query.setParameter("searchTerm", searchTerm);
            }
            courses = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error in search " + e.getMessage());
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return courses;
    }

    public List<Courses> getAllCourses() {
        Session session = HibernateUtil.getSession();
        List<Courses> courses = null;
        try {
            Query<Courses> query = session.createQuery("FROM Courses c WHERE c.approvedByAdmin = true", Courses.class);
            courses = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error in Browsing " + e.getMessage());
        } finally {
            if (session != null) {
                HibernateUtil.closeSession(session);
            }
        }
        return courses;
    }

    public List<Courses> getAllCoursesSortedByRating() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Courses> cq = cb.createQuery(Courses.class);
            Root<Courses> courseRoot = cq.from(Courses.class);
            Join<Courses, Reviews> reviewsJoin = courseRoot.join("courseReviews");
            Predicate approvedPredicate = cb.isTrue(courseRoot.get("approvedByAdmin"));
            cq.select(courseRoot).where(approvedPredicate)
                    .groupBy(courseRoot.get("courseId"))
                    .orderBy(cb.desc(cb.avg(reviewsJoin.get("rating"))));
            return session.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
