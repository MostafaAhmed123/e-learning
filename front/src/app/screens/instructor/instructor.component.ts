import { Component, inject, Output, EventEmitter, OnInit,ElementRef } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { NgIf } from '@angular/common';
import { Pipe, PipeTransform, Injectable } from '@angular/core';

import { HttpClient,HttpErrorResponse, } from '@angular/common/http';
import { UsersService } from 'src/app/services/users.service';
import { CreateCourseResponseBody } from 'src/app/models/createCourse.response.body';
import { Router } from '@angular/router';



@Component({
  selector: 'app-instructor',
  templateUrl: './instructor.component.html',
  styleUrls: ['./instructor.component.css']
})



export class InstructorComponent implements OnInit {
  @Output() formToggled = new EventEmitter<boolean>();



  name: string = '';
  duration: string = '';
  category: string = '';
  rating: number = 0;
  capacity: number = 0;
  enrolledStudents: number = 0;
  reviews: string[] = [];
  searchText: any;
  courseContent: any;
  enrollments:any [] = [
    {  Sname: 'John Doe', email: 'john@example.com' , course: 'Introduction to Programming' },
    { Sname: 'Jane Smith', email: 'jane@example.com' , course: 'Web Development' },
    { Sname: 'Ramy Waleed', email: 'ramy@example.com' , course: 'Angular' }
    // Add more enrollments here
  ];
  acceptEnrollment(enrollment: any) {
    // Logic to accept the enrollment
    console.log(`Accepted enrollment for ${enrollment.Sname}`);
  }

  rejectEnrollment(enrollment: any) {
    // Logic to reject the enrollment
    console.log(`Rejected enrollment for ${enrollment.Sname}`);
  }
  courses = [{
    id: 11, name: 'english', category: 'marketing'
  },
  {
    id: 12, name: 'arabic', category: 'marketing',
  },
  {
    id: 13, name: 'english', category: 'marketing',
  }, {
    id: 14, name: 'arabic', category: 'marketing',
  }, {
    id: 15, name: 'english', category: 'marketing',
  }, {
    id: 16, name: 'english', category: 'marketing',
  }]
  isFormOpen: boolean = false;
  courseForm!: FormGroup;
  selectedCourse: any ;
  instructorId:any;


  showDetails(coursesv2:any) {
    // now this function takes the course id and will call the api and return the whole course info
    this.selectedCourse = coursesv2;
    console.log(this.selectedCourse);
  }
  constructor(private fb: FormBuilder, private router: Router, private http: HttpClient, private elRef: ElementRef) { }
  form = this.fb.group({
    name: ''
  }



)
  ngOnInit(): void {
    this.courseForm = this.fb.group({});
    this.instructorId=localStorage.getItem('instructorId');

  }


  crateCourse() {
    let body = {
      instructorId: this.instructorId,
      name:this.name,
      duration:this.duration,
      content:this.courseContent,
      capacity:this.capacity,
      category:this.category,};

      /*
      {
    "name": "oop",
    "duration": 1,
    "content": "bla bla bla",
    "capacity": 150,
    "category": "CS",
    "instructorId": 1
}
      */
    this.http.post<CreateCourseResponseBody>(UsersService.courseUrl+UsersService.createCourseEndPoint,body).subscribe((data)=>{
      if(data)
        alert("course created successfully");
      },
      (error: HttpErrorResponse) => {
        if (error != null) {
          alert('Something Went Wrong');
        }
        console.log(error.error);

    });
}




  openForm() {
    this.isFormOpen = !this.isFormOpen;
    this.formToggled.emit(this.isFormOpen);
    this.formToggled.emit(this.isFormOpen);
    this.fb.group({
      name: [''],
      age: [''],
    })


  }

  CreateCourseApi(){

  }

}
