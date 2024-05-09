package app.Rest;



import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.Models.Admins;
import app.Models.Instructors;
import app.Models.Students;
import app.Models.Users;
import app.Services.UserService;
import app.Util.DTOs.LoginRequest;
import app.Util.DTOs.UserDTO;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class Application {
    private UserService userService = new UserService();

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


    @GET
    @Path("users")
    public List<Users> getUsers(){
        return userService.getUsers();
    }

    @GET
    @Path("user")
    public Users getUser(@QueryParam(value = "id") Long id){
        return userService.getUser(id);
    }
}
