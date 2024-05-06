package app.Models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue("admin")
public class Admins extends Users{
    public Admins(){}


    public Admins(@NotNull String name, @Size(min = 3) @Size(max = 20) @NotNull String password,
            @NotNull String email, String bio, String affiliation) {
        super(name, password, email, bio, affiliation);
    }
}
