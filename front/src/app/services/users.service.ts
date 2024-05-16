import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  static accountUrl= "http://127.0.0.1:5000";
  static courseUrl="http://localhost:8080/course-microservice-1.0/api";
  static signupEndPoint = "/users/register";
  static loginEndPoint="/users/login"
  static createCourseEndPoint="/course"
  static getAllUsersAdminEndPoint="/users"
  static editUsersAdminEndPoint="/user"
  static allCoursesAdminEndPoint = "/Allcourses"
  static getAllCoursesInstructorEndPoint="/courses"
  static getAvailableSlots="/viewAvailableSlots"
  static getReservations = "/getReservations"
  static cancelReservations = "/cancelReservation"
  static updateReservation = "/updateReservation"
}
