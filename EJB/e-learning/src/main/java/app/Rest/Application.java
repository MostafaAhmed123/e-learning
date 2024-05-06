package app.Rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.Models.Instructors;
import app.Models.Students;
import app.Models.Users;
import app.Services.AuthenticationService;
import app.Util.DTOs.LoginRequest;
import app.Util.DTOs.UserDTO;
import javax.ws.rs.Produces;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class Application {
    private AuthenticationService auth = new AuthenticationService();

    @POST
    @Path("signup")
    public boolean addUser(UserDTO wrapper) {
        return auth.signup(wrapper.isInstructor
                ? new Instructors(wrapper.name, wrapper.password, wrapper.email, wrapper.bio, wrapper.affiliation,
                        wrapper.years_of_experience)
                : new Students(wrapper.name, wrapper.password, wrapper.email, wrapper.bio, wrapper.affiliation));
    }

    @POST
    @Path("login")
    public Response login(LoginRequest request) {
        List<Users> userList = auth.login(request.email, request.password);
        if (userList != null && !userList.isEmpty())
            return Response.ok(userList.get(0)).build();
        else
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid email or password.")
                    .build();
    }
}
