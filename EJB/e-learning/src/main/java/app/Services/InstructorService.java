package app.Services;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import app.Models.CourseEnrollments;
import app.Models.Instructors;
import app.Util.HibernateUtil;
import app.Util.DTOs.EnrollmentRequestDTO;

@Stateless
public class InstructorService {
    public Instructors getInstructor(Long id) {
        Session session = null;
        Instructors instructor = null;
        try {
            session = HibernateUtil.getSession();
            instructor = session.get(Instructors.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return instructor;
    }

    public List<CourseEnrollments> getEnrollmentRequests(Long courseId) {
        List<CourseEnrollments> enrollments = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "FROM CourseEnrollments ce WHERE ce.course.courseId = :courseId AND ce.status = :stat";
            Query<CourseEnrollments> query = session.createQuery(hql, CourseEnrollments.class);
            query.setParameter("courseId", courseId);
            query.setParameter("stat", app.Util.Enums.RequestStatus.PENDING);
            enrollments = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return enrollments;
    }

    public boolean makeDecision(EnrollmentRequestDTO wrapper) {
        CourseEnrollments enrollment = null;
        Session session = HibernateUtil.getSession();
        try {
            String hql = "SELECT ce FROM CourseEnrollment ce " +
                    "WHERE ce.course.courseId = :courseId AND ce.student.studentId = :studentId";
            Query<CourseEnrollments> query = session.createQuery(hql, CourseEnrollments.class);
            query.setParameter("courseId", wrapper.courseId);
            query.setParameter("studentId", wrapper.studentId);
            enrollment = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (enrollment == null || enrollment.getStatus() != app.Util.Enums.RequestStatus.PENDING)
            return false;
        Transaction transaction = session.beginTransaction();
        enrollment.setStatus((wrapper.accept
                && enrollment.getCourse().getCapacity() > enrollment.getCourse().getCourseEnrollments().size())
                        ? app.Util.Enums.RequestStatus.ACCEPTED
                        : app.Util.Enums.RequestStatus.REUECTED);
        session.update(enrollment);
        // TODO notify student about enrollment's update
        transaction.commit();

        session.close();
        return true;

    }

}
