package app.Models;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
