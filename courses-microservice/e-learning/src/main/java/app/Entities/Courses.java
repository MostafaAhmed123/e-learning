package app.Entities;


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
    private int duration;
    private String content;
    private Long capacity;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String category;
    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    private Long popularity;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reviews> course_reviews;

    private Long instructorId;
    private boolean approvedByAdmin;

    public Courses() {
    }

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
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

    public Set<Reviews> getCourseReviews() {
        return course_reviews;
    }

    public void setCourseReviews(Set<Reviews> courseReviews) {
        this.course_reviews = courseReviews;
    }


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

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructor) {
        this.instructorId = instructor;
    }

}
