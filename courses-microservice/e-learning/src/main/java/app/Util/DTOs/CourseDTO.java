package app.Util.DTOs;

import app.Util.Enums.Status;

public class CourseDTO {
    public Long id;
    public String name;
    public int duration;
    public String content;
    public Long capacity;
    public String category;
    public Long instructorID;
    public Status status;
    public Long popularity;
    public boolean approved;
}
