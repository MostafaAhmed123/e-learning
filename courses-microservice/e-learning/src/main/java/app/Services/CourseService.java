package app.Services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.criteria.Root;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.hibernate.Transaction;

import org.hibernate.Session;
import org.hibernate.query.Query;

import app.Entities.Courses;
import app.Entities.Reviews;
import app.Util.HibernateUtil;
import app.Util.DTOs.CourseDTO;

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

    public boolean delete(Long id) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            Courses course = this.getCourse(id);
            if (course == null)
                return false;
            session.delete(course);
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

    public boolean updateCourse(CourseDTO updatedCourse) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            Courses course = this.getCourse(updatedCourse.id);
            if (course == null)
                return false;
            course.setInstructorId(updatedCourse.instructorID);
            course.setApprovedByAdmin(updatedCourse.approved);
            course.setCapacity(updatedCourse.capacity);
            course.setCategory(updatedCourse.category);
            course.setContent(updatedCourse.content);
            course.setDuration(updatedCourse.duration);
            course.setStatus(updatedCourse.status);
            course.setPopularity(updatedCourse.popularity);
            course.setName(updatedCourse.name);
            session.update(course);
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

    public boolean createCourse(Courses course) {
        Transaction transaction = null;
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:5000")
                    .path("usertype")
                    .queryParam("id", course.getInstructorId());
                String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
                System.out.println(response);
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            String role = jsonResponse.get("role").asText();
            if (isCourseExist(course.getName()) || !role.equals("instructor"))
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

    public Long getCourseCapacity(Long id) {
        Courses course = this.getCourse(id);
        if (course == null)
            return -1L;
        return course.getCapacity();
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
