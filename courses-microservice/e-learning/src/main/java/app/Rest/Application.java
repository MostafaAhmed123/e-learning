package app.Rest;

import java.util.ArrayList;
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
        course.setInstructorId(wrapper.instructorId);
        course.setApprovedByAdmin(false);
        course.setCapacity(wrapper.capacity);
        course.setCategory(wrapper.category);
        course.setContent(wrapper.content);
        course.setDuration(wrapper.duration);
        course.setStatus(app.Util.Enums.Status.CURRENT);
        course.setPopularity(0L);
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
    @Path("allcourses")
    public List<Courses> getAllCourses() {
        return crsService.getAll();
    }

    @GET
    @Path("notapproved")
    public List<Courses> notApproved() {
        return crsService.notApprovedYet();
    }

    @GET
    @Path("search")
    public List<Courses> search(@QueryParam(value = "course") String course,
            @QueryParam(value = "sorted") boolean sorted) {
        return crsService.search(course, sorted);
    }

    @DELETE
    @Path("course")
    public boolean delete(@QueryParam(value = "course") Long course, @QueryParam(value = "id") Long id) {
        return crsService.delete(course, id);
    }

    @PUT
    @Path("update")
    public boolean update(Courses course, @QueryParam(value = "id") Long id) {
        return crsService.updateCourse(course, id);
    }

    @POST
    @Path("makereview")
    public boolean makeReview(@QueryParam(value = "course") Long courseid, @QueryParam(value = "student") Long student,
            @QueryParam(value = "rate") int rate, @QueryParam(value = "feedback") String feedback) {
        System.out.println(rate);
        System.out.println(feedback);
        System.out.println(student);
        System.out.println(courseid);
        Reviews review = new Reviews();
        Courses course = crsService.getCourse(courseid);
        System.out.println(course == null ? "course not found" : "course found");
        if (course == null)
            return false;
        review.setCourse(course);
        review.setRating(rate);
        review.setReviewText(feedback);
        review.setStudentId(student);
        return reviewService.makeReview(review);
    }

    @GET
    @Path("capacity")
    public Long getCapacity(@QueryParam(value = "id") Long id) {
        return crsService.getCourseCapacity(id);
    }

    @GET
    @Path("reviews")
    public List<ReviewDTO> getReviews(@QueryParam(value = "id") Long id) {
        List<Reviews> reviews = reviewService.getReviews(id);
        List<ReviewDTO> res = new ArrayList<>();
        for (Reviews review : reviews) {
            ReviewDTO r = new ReviewDTO();
            // r.courseId = review.getCourse().getCourseId();
            r.id = review.getId();
            r.rating = review.getRating();
            r.reviewText = review.getReviewText();
            r.studentId = review.getStudentId();
            res.add(r);
        }
        return res;
    }

    @GET
    @Path("reviews")
    public List<Reviews> getReviews() {
        return reviewService.getReviews();
    }

    @GET
    @Path("sortedcourses")
    public List<Courses> getSortedCourses() {
        return crsService.getAllCoursesSortedByRating();
    }

}
