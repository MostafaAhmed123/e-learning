package app.Util.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDTO {
    public Long courseId;
    public String name;
    public int duration;
    public String content;
    public Long capacity;
    public String category;
    public Long instructorId;

}
