package app.Services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import app.Models.CourseEnrollments;
import app.Util.HibernateUtil;
import app.Util.DTOs.EnrollmentRequestDTO;

@Stateless
public class InstructorService {

    public List<CourseEnrollments> getEnrollmentRequests(Long courseId) {
        List<CourseEnrollments> enrollments = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "FROM CourseEnrollments ce WHERE ce.course.courseId = :courseId AND ce.status = :stat AND ce.course.approvedByAdmin = true";
            Query<CourseEnrollments> query = session.createQuery(hql, CourseEnrollments.class);
            query.setParameter("courseId", courseId);
            query.setParameter("stat", app.Util.Enums.RequestStatus.PENDING);
            enrollments = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                HibernateUtil.closeSession(session);
        }
        return enrollments;
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
        }
        if (enrollment == null || enrollment.getStatus() != app.Util.Enums.RequestStatus.PENDING)
            return false;
        Transaction transaction = session.beginTransaction();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080")
                .path("course-microservice/api/capacity")
                .queryParam("id", enrollment.getId().getCourseId());
        Long response = target.request(MediaType.APPLICATION_JSON).get(Long.class);
        enrollment.setStatus((wrapper.accept
                && response > enrollments.size())
                        ? app.Util.Enums.RequestStatus.ACCEPTED
                        : app.Util.Enums.RequestStatus.REUECTED);
        session.update(enrollment);
        // TODO notify student about enrollment's update
        client.close();
        transaction.commit();

        HibernateUtil.closeSession(session);
        return true;

    }

}
