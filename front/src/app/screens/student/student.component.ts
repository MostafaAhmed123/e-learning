import { Component, OnInit } from '@angular/core';
import { HttpClient,HttpErrorResponse, } from '@angular/common/http';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';
import { GetAllCoursesInstructorResponseBody } from 'src/app/models/getAllCoursesInstructor.response.body';


@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  ngOnInit(): void {
    this.currentEnrollments = [
      { courseName: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35 },
      { courseName: 'Web Development', category: 'Web Development', duration: '8 weeks', rating: 4.2, capacity: 40, enrolledStudents: 30 }
    ];

    this.pastEnrollments = [
      { courseName: 'Data Science', category: 'Data Science', duration: '10 weeks', rating: 4.8, capacity: 30, enrolledStudents: 25 },
      { courseName: 'Machine Learning', category: 'Machine Learning', duration: '12 weeks', rating: 4.7, capacity: 25, enrolledStudents: 20 }
    ];
    this.getAllCourses();
  }

  constructor( private router: Router, private http: HttpClient, ) { }


  courses:Array<GetAllCoursesInstructorResponseBody>=[];
  sorted :boolean=false;


  showReviewForm: boolean = false;
  rating: number = 0;
  review: string = '';

  openReviewForm() {
    this.showReviewForm = !this.showReviewForm;
  }
  selectedCourse: any ;
  enroll(courseId: number) {
    // Logic for enrolling in the course
    console.log('Enrolled in course with ID:', courseId);
  }
  cancelEnrollment(enrollment: any) {
    // Implement cancellation logic
    console.log('Cancelled enrollment:', enrollment);
    // i will remove the enrollment from the currentEnrollments array
  }
  showDetails(coursesv2:GetAllCoursesInstructorResponseBody) {
    // now this function takes the course id and will call the api and return the whole course info
    this.selectedCourse = coursesv2;
    console.log(this.selectedCourse);
  }
  searchText: any;
  currentEnrollments: any[] = [];
  pastEnrollments: any[] = [];

  showMessageClicked: boolean = false;
  message: string = 'This is a notification message!';

  showMessage() {
    this.showMessageClicked = true;
    setTimeout(() => {
      this.showMessageClicked = false;
    }, 3000);
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
  saerchBySort(){
    this.sorted != this.sorted;
    const url = `${UsersService.courseUrl}${UsersService.searchBySort}?course=${this.searchText}&sorted=${this.sorted}`;
    this.http.get<GetAllCoursesInstructorResponseBody[]>(url).subscribe((data) => {
      if (data != null) {
        console.log(data);
      }
    });
  }
  getEnrollments(){

  }


}
