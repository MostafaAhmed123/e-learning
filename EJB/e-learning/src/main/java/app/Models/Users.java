package app.Models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.DiscriminatorType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String name;
    @Size(min = 3)
    @Size(max = 20)
    @NotNull
    private String password;
    @NotNull
    private String email;
    private String bio;
    private String affiliation;

    @Column(name = "user_type", insertable = false, updatable = false)
    private String user_type;

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String userType) {
        this.user_type = userType;
    }

    public Users(@NotNull String name, @Size(min = 3) @Size(max = 20) @NotNull String password, @NotNull String email,
            String bio, String affiliation) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.affiliation = affiliation;
    }

    public Long getUserId() {
        return userId;
    }

    public Users() {
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

}
