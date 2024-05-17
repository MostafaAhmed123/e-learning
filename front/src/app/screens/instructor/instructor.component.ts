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
import { GetAllCoursesInstructorResponseBody } from 'src/app/models/getAllCoursesInstructor.response.body';
import { GetEnrollmentsInstructorResponseBody } from 'src/app/models/getEnrollmentsInstructor.response.body';
import { GetEnrollmentsHelperResponseBody } from 'src/app/models/getEnrollmentsHelper.response.body';



@Component({
  selector: 'app-instructor',
  templateUrl: './instructor.component.html',
  styleUrls: ['./instructor.component.css']
})



export class InstructorComponent implements OnInit {
  @Output() formToggled = new EventEmitter<boolean>();

  //lw bazet a3mlha response body lwa7do
  courses:Array<GetAllCoursesInstructorResponseBody>=[];
  sorted :boolean=false;
  name: string = '';
  duration: string = '';
  category: string = '';
  rating: number = 0;
  capacity: number = 0;
  enrolledStudents: number = 0;
  reviews: string[] = [];
  searchText: any;
  courseContent: any;
  accept:boolean=false;

  enrollments:Array<GetEnrollmentsHelperResponseBody>=[];
  acceptEnrollment(enrollment: any) {
    this.accept=true;
    let body = {
      studentId:enrollment.id?.userId,
      courseId:enrollment.id?.courseId,
      accept:this.accept

    };

    const url = `${UsersService.enrollmentUrl}${UsersService.acceptOrDenyApi}?id=${this.instructorId}`;
    this.http.put<boolean>(url,body).subscribe((data) => {
      if (data != null) {
        console.log(data);
        location.reload();
      }
    });


  }

  rejectEnrollment(enrollment: any) {
    this.accept=false;
    let body = {
      studentId:enrollment.id?.userId,
      courseId:enrollment.id?.courseId,
      accept:this.accept

    };

    const url = `${UsersService.enrollmentUrl}${UsersService.acceptOrDenyApi}?id=${this.instructorId}`;
    this.http.put<boolean>(url,body).subscribe((data) => {
      if (data != null) {
        console.log(data);
        location.reload();

      }
    });
  }

  isFormOpen: boolean = false;
  courseForm!: FormGroup;
  selectedCourse: any ;
  instructorId:any;



  showDetails(coursesv2:GetAllCoursesInstructorResponseBody) {
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
    this.getAllCourses();
    this.getEnrollments();

  }


  getAllCourses(){
    this.http
    .get<GetAllCoursesInstructorResponseBody[]>(UsersService.courseUrl + UsersService.getAllCoursesInstructorEndPoint)
    .subscribe((data) => {
      if (data != null) {
        this.courses=data;
      }
    });

  }
  searchBySort(){
    this.sorted != this.sorted;
    const url = `${UsersService.courseUrl}${UsersService.searchBySort}?course=${this.searchText}&sorted=${this.sorted}`;
    this.http.get<GetAllCoursesInstructorResponseBody[]>(url).subscribe((data) => {
      if (data != null) {
        console.log(data);
      }
    });
  }

  crateCourse() {
    let body = {
      instructorId: this.instructorId,
      name:this.name,
      duration:this.duration,
      content:this.courseContent,
      capacity:this.capacity,
      category:this.category,};

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

//getEnrollmentsInstructorApi
  getEnrollments(){
    const url = `${UsersService.enrollmentUrl}${UsersService.getEnrollmentsInstructorApi}?id=${this.instructorId}`;
    this.http.get<GetEnrollmentsHelperResponseBody[]>(url).subscribe((data) => {
      if (data != null) {
        console.log(data);
        this.enrollments=data;
      }
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



}
