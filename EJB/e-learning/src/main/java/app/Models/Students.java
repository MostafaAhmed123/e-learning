package app.Models;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue("student")
public class Students extends Users {
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private Set<Notifications> notifications;

    @ManyToMany(mappedBy = "students")
    private Set<Courses> courses;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private Set<Reviews> reviews;

    public Students(){}


    public Students(@NotNull String name, @Size(min = 3) @Size(max = 20) @NotNull String password,
            @NotNull String email, String bio, String affiliation) {
        super(name, password, email, bio, affiliation);
    }


    public Set<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notifications> notifications) {
        this.notifications = notifications;
    }

    public Set<Courses> getCourses() {
        return courses;
    }

    public void setCourses(Set<Courses> courses) {
        this.courses = courses;
    }

    public Set<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Reviews> reviews) {
        this.reviews = reviews;
    }


}
