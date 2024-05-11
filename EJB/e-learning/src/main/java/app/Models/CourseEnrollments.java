package app.Models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import app.Util.Enums.RequestStatus;

@Entity
public class CourseEnrollments {
    @EmbeddedId
    private CourseEnrollmentId id;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;


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
        status = app.Util.Enums.RequestStatus.PENDING;
    }

}
