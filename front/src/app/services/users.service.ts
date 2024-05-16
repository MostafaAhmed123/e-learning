import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  static accountUrl= "http://127.0.0.1:5000";
  static courseUrl="http://localhost:8080/course-microservice-1.0";
  static signupEndPoint = "/users/register";
  static loginEndPoint="/users/login"
  static createCourseEndPoint="/api/course"
  static getDoctorSlots="/viewSlots"
  static getDoctors="/getDoctors"
  static reserveApt = "/reserveSlot"
  static getAvailableSlots="/viewAvailableSlots"
  static getReservations = "/getReservations"
  static cancelReservations = "/cancelReservation"
  static updateReservation = "/updateReservation"
}
