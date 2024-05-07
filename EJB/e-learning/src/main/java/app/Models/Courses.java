package app.Models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import app.Util.Enums.Status;

@Entity
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    private double duration;
    private String content;
    private Long capacity;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String category;

    private boolean approvedByAdmin;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private Instructors instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CourseEnrollments> courseEnrollments;

    public Courses() {
    }

    // public Set<Notifications> getNotifications() {
    //     return notifications;
    // }

    // public void setNotifications(Set<Notifications> notifications) {
    //     this.notifications = notifications;
    // }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // public Set<Reviews> getCourseReviews() {
    //     return courseReviews;
    // }

    // public void setCourseReviews(Set<Reviews> courseReviews) {
    //     this.courseReviews = courseReviews;
    // }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;

    }

    public boolean getApprovedByAdmin() {
        return approvedByAdmin;
    }

    public void setApprovedByAdmin(boolean approvedByAdmin) {
        this.approvedByAdmin = approvedByAdmin;
    }

    public Instructors getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructors instructor) {
        this.instructor = instructor;
    }

    public Set<CourseEnrollments> getCourseEnrollments() {
        return courseEnrollments;
    }

    public void setCourseEnrollments(Set<CourseEnrollments> courseEnrollments) {
        this.courseEnrollments = courseEnrollments;
    }

}
