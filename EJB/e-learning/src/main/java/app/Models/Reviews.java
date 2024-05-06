package app.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Courses course;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Students getStudent() {
        return student;
    }
    public void setStudent(Students student) {
        this.student = student;
    }
    public Courses getCourse() {
        return course;
    }
    public void setCourse(Courses course) {
        this.course = course;
    }
    public String getReviewText() {
        return reviewText;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    private String reviewText;
    private int rating;

    public Reviews() {
    }

}
