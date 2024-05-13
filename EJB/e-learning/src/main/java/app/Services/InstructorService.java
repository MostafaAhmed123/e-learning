package app.Services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.ws.rs.client.Entity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.ws.rs.core.Response;
import app.Models.CourseEnrollments;
import app.Models.Notifications;
import app.Util.HibernateUtil;
import app.Util.DTOs.EnrollmentRequestDTO;

@Stateless
public class InstructorService {

    public List<CourseEnrollments> getEnrollmentRequests(Long courseId) {
        List<CourseEnrollments> res = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            String hql = "FROM CourseEnrollments ce WHERE ce.id.courseId = :courseId AND ce.status = :stat";
            Query<CourseEnrollments> query = session.createQuery(hql, CourseEnrollments.class);
            query.setParameter("courseId", courseId);
            query.setParameter("stat", app.Util.Enums.RequestStatus.PENDING);
            List<CourseEnrollments> enrollments = query.getResultList();
            Client client = ClientBuilder.newClient();
            WebTarget target;
            for(CourseEnrollments enrollment : enrollments){
                target = client.target("http://localhost:8080")
                .path("course-microservice-1.0/api/course")
                .queryParam("id", enrollment.getId().getCourseId());
                String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse;
                jsonResponse = objectMapper.readTree(response);
                boolean approvedByAdmin = jsonResponse.get("approvedByAdmin").asBoolean();
                if(approvedByAdmin)
                    res.add(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return res.isEmpty() ? null : res;
    }

    public boolean makeDecision(EnrollmentRequestDTO wrapper) {
        CourseEnrollments enrollment = null;
        List<CourseEnrollments> enrollments = null;
        Session session = HibernateUtil.getSession();

        try {
            String hql = "SELECT ce FROM CourseEnrollments ce " +
                    "WHERE ce.id.courseId = :courseId AND ce.id.userId = :studentId";
            Query<CourseEnrollments> query = session.createQuery(hql, CourseEnrollments.class);
            query.setParameter("courseId", wrapper.courseId);
            query.setParameter("studentId", wrapper.studentId);
            enrollment = query.uniqueResult();
            hql = "FROM CourseEnrollments WHERE id.courseId = :id";
            query = session.createQuery(hql, CourseEnrollments.class);
            query.setParameter("id", wrapper.courseId);
            enrollments = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (enrollment == null || enrollment.getStatus() != app.Util.Enums.RequestStatus.PENDING)
            return false;
        Transaction transaction = session.beginTransaction();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080")
                .path("course-microservice-1.0/api/course")
                .queryParam("id", enrollment.getId().getCourseId());
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse;
        try {
            jsonResponse = objectMapper.readTree(response);
            Long capacity = jsonResponse.get("capacity").asLong();
            Long popularity = jsonResponse.get("popularity").asLong();
            enrollment.setStatus((wrapper.accept
                    && capacity > enrollments.size())
                            ? app.Util.Enums.RequestStatus.ACCEPTED
                            : app.Util.Enums.RequestStatus.REUECTED);
            session.update(enrollment);
            if (wrapper.accept) {
                popularity++;
                ((ObjectNode) jsonResponse).put("popularity", popularity);
                String updatedJsonString = objectMapper.writeValueAsString(jsonResponse);
                target = client.target("http://localhost:8080").path("course-microservice-1.0/api/update");
                Response res = target.request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(updatedJsonString, MediaType.APPLICATION_JSON));
                if (res.getStatus() != 200)
                    throw new Exception("responce code: " + res.getStatus());
            }
            Notifications notification = new Notifications();
            notification.setStudentId(wrapper.studentId);
            notification.setNotification("your enrollment request to course " + wrapper.courseId + " has been "
                    + (wrapper.accept ? "accepted" : "rejected"));
            NotificationService service = new NotificationService();
            if (service.createNotification(notification))
                throw new Exception("notification cannot be created");
            client.close();
            transaction.commit();
            HibernateUtil.closeSession(session);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
