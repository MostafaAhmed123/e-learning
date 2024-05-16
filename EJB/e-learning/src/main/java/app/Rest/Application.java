package app.Rest;



import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import app.Services.AdminService;
import app.Services.EnrollmentService;
import app.Services.InstructorService;
import app.Services.NotificationService;
import app.Util.DTOs.EnrollmentRequestDTO;
import app.Models.CourseEnrollments;
import app.Models.Notifications;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class Application {
    InstructorService service = new InstructorService();
    EnrollmentService enrollService = new EnrollmentService();
    NotificationService notifiy = new NotificationService();
    AdminService admin = new AdminService();
    @GET
    @Path("enrollments")
    public List<CourseEnrollments> getEnrollments(@QueryParam(value = "course") Long course, @QueryParam(value = "id") Long id){
        return service.getEnrollmentRequests(course, id);
    }

    @PUT
    @Path("makedecision")
    public boolean makeDecision(EnrollmentRequestDTO wrapper, @QueryParam(value = "id") Long id){
        return service.makeDecision(wrapper, id);
    }

    @POST
    @Path("enroll")
    public boolean enroll(EnrollmentRequestDTO enrollment){
        return enrollService.enroll(enrollment);
    }

    @DELETE
    @Path("enroll")
    public boolean cancel(EnrollmentRequestDTO enrollment){
        return enrollService.cancel(enrollment);
    }

    @GET
    @Path("enrollments")
    public List<CourseEnrollments> getEnrollments(){
        return enrollService.getEnrollments();
    }

    @GET
    @Path("studentenrollments")
    public List<CourseEnrollments> getStudentEnrollments(@QueryParam(value = "id") Long id){
        return enrollService.getEnrollments(id);
    }

    @GET
    @Path("instructorenrollments")
    public List<CourseEnrollments> getInstructorEnrollments(@QueryParam(value = "id") Long id){
        return enrollService.getInstructorEnrollments(id);
    }

    @GET
    @Path("notifications")
    public List<Notifications> getNotifications(@QueryParam(value = "id") Long id){
        return notifiy.getNotifications(id);
    }

    @GET
    @Path("activities")
    public List<List<? extends Object>> getStat(@QueryParam(value = "id") Long id){
        try {
            return admin.getActivityLog(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
