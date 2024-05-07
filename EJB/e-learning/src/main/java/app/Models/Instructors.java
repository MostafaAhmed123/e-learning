package app.Models;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue("instructor")
public class Instructors extends Users {
    // @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private Set<Courses> courses;
    private int years_of_experience;

    public Instructors() {}

    public Instructors(@NotNull String name, @Size(min = 3) @Size(max = 20) @NotNull String password,
            @NotNull String email, String bio, String affiliation, int years_of_experience) {
        super(name, password, email, bio, affiliation);
        this.years_of_experience = years_of_experience;
    }

    public int getYears_of_experience() {
        return years_of_experience;
    }

    public void setYears_of_experience(int years_of_experience) {
        this.years_of_experience = years_of_experience;
    }

    // public Set<Courses> getCourses() {
    //     return courses;
    // }

    // public void setCourses(Set<Courses> courses) {
    //     this.courses = courses;
    // }


}
