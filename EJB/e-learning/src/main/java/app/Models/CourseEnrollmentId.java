package app.Models;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class CourseEnrollmentId implements Serializable {
    private Long userId;
    private Long courseId;

    public CourseEnrollmentId() {

    }

    public CourseEnrollmentId(Long userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEnrollmentId that = (CourseEnrollmentId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId);
    }
}
