package app.Services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Entities.Reviews;
import app.Util.HibernateUtil;
import app.Util.DTOs.EnrollmentsDTO;

@Stateless
public class ReviewService {
    public List<Reviews> getReviews(Long id) {
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

    public List<Reviews> getReviews() {
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

    public boolean makeReview(Reviews review) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            String hql = "FROM Reviews r WHERE r.studentId = :id AND r.course.courseId =:crs";
            Query<Reviews> query = session.createQuery(hql, Reviews.class);
            query.setParameter("id", review.getStudentId());
            query.setParameter("crs", review.getCourse());
            List<Reviews> tmp = query.getResultList();
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:5000")
                    .path("usertype")
                    .queryParam("id", review.getStudentId());
            String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            String role = jsonResponse.get("role").asText();
            target = client.target("http://localhost:8080").path("E-LEARNING-1.0/studentenrollments").queryParam("id",
                    review.getStudentId());
            List<EnrollmentsDTO> enrollments = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<EnrollmentsDTO>>(){});
            boolean found = false;
            for(EnrollmentsDTO enrollment : enrollments)
                if(enrollment.getUserId() == review.getStudentId())
                    found = true;
            if (!tmp.isEmpty() || review.getCourse().getStatus() != app.Util.Enums.Status.DONE
                    || !role.equals("student") || !found)
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
