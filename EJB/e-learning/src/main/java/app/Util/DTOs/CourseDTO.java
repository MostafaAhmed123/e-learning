package app.Util.DTOs;

import java.util.Set;

public class CourseDTO {
    public Set<ReviewDTO> course_reviews;
    public String name;
    public Long popularity, instructorId;
}
