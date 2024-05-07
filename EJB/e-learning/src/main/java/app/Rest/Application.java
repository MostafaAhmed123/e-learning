package app.Rest;



import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.Models.Admins;
import app.Models.Courses;
import app.Models.Instructors;
import app.Models.Students;
import app.Models.Users;
import app.Services.UserService;
import app.Services.CourseService;
import app.Util.DTOs.CourseDTO;
import app.Util.DTOs.LoginRequest;
import app.Util.DTOs.UserDTO;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class Application {
    private UserService userService = new UserService();
    private CourseService crsService = new CourseService();

    @POST
    @Path("signup")
    public boolean addUser(UserDTO wrapper) {
        return userService.signup(wrapper.isAdmin ? new Admins(wrapper.name, wrapper.password, wrapper.email, null, null)
                : (wrapper.isInstructor
                        ? new Instructors(wrapper.name, wrapper.password, wrapper.email, wrapper.bio,
                                wrapper.affiliation,
                                wrapper.years_of_experience)
                        : new Students(wrapper.name, wrapper.password, wrapper.email, wrapper.bio,
                                wrapper.affiliation)));
    }

    @POST
    @Path("login")
    public Response login(LoginRequest request) {
        List<Users> users = userService.login(request.email, request.password);
        if (users != null && !users.isEmpty())
            return Response.ok(users.get(0)).build();
        else
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid email or password.")
                    .build();
    }

    @POST
    @Path("course")
    public boolean createCourse(CourseDTO wrapper){
        Courses course = new Courses();
        Users instructor = userService.getUser(wrapper.instructorID);
        if(instructor == null || instructor.getUser_type() != "instructor")
            return false;
        course.setInstructor((Instructors) instructor);
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
    public Courses getCourse(@QueryParam(value = "id") Long id){
        return crsService.getCourse(id);
    }

    @GET
    @Path("courses")
    public List<Courses> getCourses(){
        return crsService.getAllCourses();
    }

    @GET
    @Path("search")
    public List<Courses> search(@QueryParam(value = "course") String course, @QueryParam(value = "byName") boolean byName){
        return crsService.search(course, byName);
    }

    @GET
    @Path("users")
    public List<Users> getUsers(){
        return userService.getUsers();
    }
}
