package app.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Courses course;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long student) {
        this.studentId = student;
    }
    // public Courses getCourse() {
    //     return course;
    // }
    // public void setCourse(Courses course) {
    //     this.course = course;
    // }
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
        if(rating >= 0 && rating <= 5)
            this.rating = rating;
        else
            this.rating = 0;
    }
    private String reviewText;
    @Min(0)
    @Max(5)
    private int rating;

    public Reviews() {
    }

}
