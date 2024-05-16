import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  static accountUrl= "http://127.0.0.1:5000";
  static courseUrl="http://localhost:5000";
  static signupEndPoint = "/users/register";
  static loginEndPoint="/users/login"
  static addslotEndPoint="/addSlot"  
  static getDoctorSlots="/viewSlots"
  static getDoctors="/getDoctors"
  static reserveApt = "/reserveSlot"
  static getAvailableSlots="/viewAvailableSlots"
  static getReservations = "/getReservations"
  static cancelReservations = "/cancelReservation"
  static updateReservation = "/updateReservation"
}
