package app.Services;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Models.CourseEnrollmentId;
import app.Models.CourseEnrollments;
import app.Util.HibernateUtil;
import app.Util.DTOs.EnrollmentRequestDTO;

public class EnrollmentService {
    public boolean enroll(EnrollmentRequestDTO enrollrequest) {
        Transaction transaction = null;
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:5000")
                    .path("usertype")
                    .queryParam("id", enrollrequest.studentId);
            String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            String role = jsonResponse.get("role").asText();
            target = client.target("http://localhost:8080").path("course").queryParam("id", enrollrequest.courseId);
            response = target.request(MediaType.APPLICATION_JSON).get(String.class);
            jsonResponse = objectMapper.readTree(response);
            String course = jsonResponse.get("status").asText();
            boolean status = jsonResponse.get("approvedByAdmin").asBoolean();
            if (course.equals("DONE") || !role.equals("instructor") || !status)
                return false;
            CourseEnrollments enrollment = new CourseEnrollments();
            CourseEnrollmentId id = new CourseEnrollmentId(enrollrequest.studentId, enrollrequest.courseId);
            enrollment.setId(id);
            session.save(enrollment);
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

    public boolean cancel(EnrollmentRequestDTO wrapper){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            String hql = "SELECT ce FROM CourseEnrollments ce " +
                    "WHERE ce.id.courseId = :courseId AND ce.id.userId = :studentId";
            Query<CourseEnrollments> query = session.createQuery(hql, CourseEnrollments.class);
            query.setParameter("courseId", wrapper.courseId);
            query.setParameter("studentId", wrapper.studentId);
            CourseEnrollments enrollment = query.uniqueResult();
            if (enrollment == null)
                return false;
            session.delete(enrollment);
            transaction.commit();
            HibernateUtil.closeSession(session);
        }catch(Exception e){
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        }
        return true;
    }

    public List<CourseEnrollments> getEnrollments(){
        Session session = null;
        List<CourseEnrollments> enrollments = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "FROM CourseEnrollments";
            Query<CourseEnrollments> query = session.createQuery(hql, CourseEnrollments.class);
            enrollments = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return enrollments;
    }

    public List<CourseEnrollments> getEnrollments(Long id){
        Session session = null;
        List<CourseEnrollments> enrollments = null;
        try {
            session = HibernateUtil.getSession();
            String hql = "FROM CourseEnrollments ce WHERE ce.id.userId =:studentid";
            Query<CourseEnrollments> query = session.createQuery(hql, CourseEnrollments.class);
            query.setParameter("studentid", id);
            enrollments = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return enrollments;
    }
}
