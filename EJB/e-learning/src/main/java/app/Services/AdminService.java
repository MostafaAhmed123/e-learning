package app.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Models.CourseEnrollments;
import app.Util.DTOs.CourseDTO;
import app.Util.DTOs.ReviewDTO;

@Stateless
public class AdminService {
    public List<List<? extends Object>> getActivityLog(Long id) throws JsonMappingException, JsonProcessingException{
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:5000")
                .path("usertype")
                .queryParam("id", id);
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse;
            jsonResponse = objectMapper.readTree(response);
            response = jsonResponse.get("role").asText();
            if(!response.equals("admin"))
                return Arrays.asList(Arrays.asList("UNAUTHORIZED ACCESS"));
        EnrollmentService helper = new EnrollmentService();
        List<CourseEnrollments> enrollments = helper.getEnrollments();
        target = client.target("http://localhost:8080").path("course-microservice-1.0/api/courses");
        List<CourseDTO> courses = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<CourseDTO>>(){});
        target = client.target("http://localhost:8080").path("course-microservice-1.0/api/reviews");
        List<Object> courseRes = new ArrayList<>();
        for(CourseDTO course : courses){
            target.queryParam("id", course.courseId);
            System.out.println(course);
            List<ReviewDTO> reviews = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<ReviewDTO>>(){});
            courseRes.add(Arrays.asList(course, reviews));
        }
        return Arrays.asList(enrollments, courseRes);
    }
}
