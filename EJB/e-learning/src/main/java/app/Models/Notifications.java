package app.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Students student;

    // TODO replace it with enrollment id
    // @ManyToOne
    // @JoinColumn(name = "courseId")
    // private Courses course;
    public Notifications() {}
    private String notification;
    public Long getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    public Students getStudent() {
        return student;
    }
    public void setStudent(Students student) {
        this.student = student;
    }
    // public Courses getCourse() {
    //     return course;
    // }
    // public void setCourse(Courses course) {
    //     this.course = course;
    // }
    public String getNotification() {
        return notification;
    }
    public void setNotification(String notification) {
        this.notification = notification;
    }
}
