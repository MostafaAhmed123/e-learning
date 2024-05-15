import { Component, inject, Output, EventEmitter, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { NgIf } from '@angular/common';
import { Pipe, PipeTransform, Injectable } from '@angular/core';




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

  showDetails(coursesv2:any) {
    // now this function takes the course id and will call the api and return the whole course info
    this.selectedCourse = coursesv2;
    console.log(this.selectedCourse);
  }
  constructor(private fb: FormBuilder) { }
  form = this.fb.group({
    name: ''
  })
  ngOnInit(): void {
    this.courseForm = this.fb.group({

    })
  }

  addReview(review: string) {
    this.reviews.push(review);
  }

  onSubmit() { }
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
