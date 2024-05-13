package app.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private Long studentId;

    public Notifications() {}
    private String notification;
    public Long getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long student) {
        this.studentId = student;
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
