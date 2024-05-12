package app.Services;

import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import app.Models.CourseEnrollments;
import app.Util.DTOs.CourseDTO;

@Stateless
public class AdminService {
    public List<List<? extends Object>> getActivityLog(Long id){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:5000")
                .path("usertype")
                .queryParam("id", id);
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
            if(!response.equals("admin"))
                return Arrays.asList(Arrays.asList("UNAUTHORIZED ACCESS"));
        EnrollmentService helper = new EnrollmentService();
        List<CourseEnrollments> enrollments = helper.getEnrollments();
        target = client.target("http://localhost:8080").path("courses");
        List<CourseDTO> courses = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<CourseDTO>>(){});
        return Arrays.asList(enrollments, courses);
    }
}
