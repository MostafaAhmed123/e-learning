package app.Util.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO {
    public Long studentId, id;
    public String reviewText;
    public int rating;
}
