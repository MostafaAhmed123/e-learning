package app.Models;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import app.Util.Enums.RequestStatus;

@Entity
public class CourseEnrollments {
    @EmbeddedId
    private CourseEnrollmentId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "userId")
    private Students student;

    private Long course;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Long getCourse() {
        return course;
    }

    public void setCourse(Long course) {
        this.course = course;
    }

    public CourseEnrollmentId getId() {
        return id;
    }

    public void setId(CourseEnrollmentId id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public CourseEnrollments() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseEnrollments that = (CourseEnrollments) o;
        return Objects.equals(student, that.student) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }
}
