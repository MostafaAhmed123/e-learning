package app.Rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import app.Entities.Courses;
import app.Entities.Reviews;
import app.Services.CourseService;
import app.Services.ReviewService;
import app.Util.DTOs.CourseDTO;
import app.Util.DTOs.ReviewDTO;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class Application {
    private CourseService crsService = new CourseService();
    private ReviewService reviewService = new ReviewService();

    @POST
    @Path("course")
    public boolean createCourse(CourseDTO wrapper) {
        Courses course = new Courses();

        course.setInstructorId(wrapper.instructorID);
        course.setApprovedByAdmin(false);
        course.setCapacity(wrapper.capacity);
        course.setCategory(wrapper.category);
        course.setContent(wrapper.content);
        course.setDuration(wrapper.duration);
        course.setStatus(app.Util.Enums.Status.CURRENT);
        course.setName(wrapper.name);
        return crsService.createCourse(course);
    }

    @GET
    @Path("course")
    public Courses getCourse(@QueryParam(value = "id") Long id) {
        return crsService.getCourse(id);
    }

    @GET
    @Path("courses")
    public List<Courses> getCourses() {
        return crsService.getAllCourses();
    }

    @GET
    @Path("search")
    public List<Courses> search(@QueryParam(value = "course") String course,
            @QueryParam(value = "byName") boolean byName) {
        return crsService.search(course, byName);
    }

    @DELETE
    @Path("course")
    public boolean delete(@QueryParam(value = "id") Long id) {
        return crsService.delete(id);
    }

    @PUT
    @Path("update")
    public boolean update(CourseDTO course) {
        return crsService.updateCourse(course);
    }

    @GET
    @Path("makereview")
    public boolean makeReview(ReviewDTO wrapper) {
        Reviews review = new Reviews();
        Courses course = crsService.getCourse(wrapper.courseId);
        if (course == null)
            return false;
        review.setCourse(course);
        review.setRating(wrapper.rating);
        review.setReviewText(wrapper.review);
        review.setStudentId(wrapper.studentId);
        return reviewService.makeReview(review);
    }

    @GET
    @Path("capacity")
    public Long getCapacity(@QueryParam(value = "id") Long id) {
        return crsService.getCourseCapacity(id);
    }

}
