package app.Util.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollmentRequestDTO {
    public Long studentId, courseId;
    public boolean accept;
}
