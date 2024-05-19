import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  static accountUrl= "http://127.0.0.1:5000";
  static courseUrl="http://localhost:8080/course-microservice-1.0/api";
  static enrollmentUrl="http://localhost:8080/E-LEARNING-1.0/api";
  static signupEndPoint = "/users/register";
  static loginEndPoint="/users/login"
  static createCourseEndPoint="/course"
  static getAllUsersAdminEndPoint="/users"
  static editUsersAdminEndPoint="/user"
  static allCoursesAdminEndPoint = "/allcourses"
  static getAllCoursesInstructorEndPoint="/courses"
  static editCoursesAdminEndPoint="/update"
  static deleteCoursesAdminEndPoint="/course"
  static searchBySort = "/search"
  static getEnrollmentsInstructorApi="/instructorenrollments"
  static acceptOrDenyApi="/makedecision"
  static cancelReservations = "/cancelReservation"
  static notificationStudentApi="/notifications"
  static pastEnrollmentsApi="/pastenrollments"
  static currentEnrollmentsApi="/currentenrollments"
  static deleteEnrollmentApi = "/enroll"
  static makeReviewApi="/makereview"
}
