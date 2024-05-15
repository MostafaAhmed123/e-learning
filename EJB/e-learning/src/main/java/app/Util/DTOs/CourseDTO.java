package app.Util.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDTO {
    public String name;
    public Long popularity, instructorId;
    public Long courseId;
    public int duration;
    public String content;
    public Long capacity;
    public String status;
    public String category;
    public double rating;
    public boolean approvedByAdmin;
}
