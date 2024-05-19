package app.Util.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollmentsDTO {
    private tempDTO id;
    private String status;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public tempDTO getId() {
        return id;
    }

    public void setId(tempDTO id) {
        this.id = id;
    }
}
