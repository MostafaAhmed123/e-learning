import { Component, OnInit } from '@angular/core';
import { HttpClient,HttpErrorResponse, } from '@angular/common/http';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';
import { GetAllCoursesInstructorResponseBody } from 'src/app/models/getAllCoursesInstructor.response.body';
import { NotificationStudentResponseBody } from 'src/app/models/notificationStudent.response.body';
import { PastCurrentEnrollmentsReponseBody } from 'src/app/models/pastCurrentEnrollments.response.body';
import { CreateCourseResponseBody } from 'src/app/models/createCourse.response.body';


@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  ngOnInit(): void {

    this.getAllCourses();
    this.studentId=localStorage.getItem('studentId');
    this.getPastEnrollments();
    this.getCurrentEnrollments();

  }

  constructor( private router: Router, private http: HttpClient, ) { }


  courses:Array<GetAllCoursesInstructorResponseBody>=[];
  sorted :boolean=false;
  studentId:any;

  showReviewForm: boolean = false;
  rating: number = 0;
  review: string = '';

  openReviewForm() {

    this.showReviewForm = !this.showReviewForm;
  }
  selectedCourse: any ;
  enroll(courseIdv2: any) {
    // let body = {
    //   studentId: this.studentId,
    //   courseId:courseIdv2,
    //  };
     const url = `${UsersService.enrollmentUrl}${UsersService.deleteEnrollmentApi}?course=${courseIdv2}&student=${this.studentId}`;


    this.http.post<boolean>(url, null).subscribe((data)=>{
      if(data!=false)
      {
        console.log(data);
        alert("Enrollment request sent successfully");
      }
      else{
        alert("Connot register in past course");
      }
      }
    );
 console.log('Enrolled in course with ID:', courseIdv2);
  }
  cancelEnrollment(enrollment: any) {
    let body = {
      studentId: this.studentId,
      courseId:enrollment.id?.courseId,
     };

    this.http.delete<boolean>(UsersService.enrollmentUrl+UsersService.deleteEnrollmentApi,{
      body
    }).subscribe((data)=>{
      if(data)
       console.log(data);
      location.reload();
      },
      (error: HttpErrorResponse) => {
        if (error != null) {
          alert('Something Went Wrong');
        }
        console.log(error.error);

    });
    console.log('Cancelled enrollment:', enrollment);

  }
  showDetails(coursesv2:GetAllCoursesInstructorResponseBody) {

    this.selectedCourse = coursesv2;
    console.log(this.selectedCourse);
  }
  searchText: any;
  pastEnrollments:Array<PastCurrentEnrollmentsReponseBody>=[];
  currentEnrollments:Array<PastCurrentEnrollmentsReponseBody>=[];
  showMessageClicked: boolean = false;
  message:Array<NotificationStudentResponseBody>=[];
  flag:boolean=false;
  showMessage() {
    this.showMessageClicked = true;
   this.getNotifications();
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
  getNotifications(){
    const url = `${UsersService.enrollmentUrl}${UsersService.notificationStudentApi}?id=${this.studentId}`;
    this.http.get<NotificationStudentResponseBody[]>(url).subscribe((data) => {
      if (data != null) {
        this.message=data;

        console.log(data);
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
  getPastEnrollments(){
    const url = `${UsersService.enrollmentUrl}${UsersService.pastEnrollmentsApi}?id=${this.studentId}`;
    this.http.get<PastCurrentEnrollmentsReponseBody[]>(url).subscribe((data) => {
      if (data != null) {
        this.pastEnrollments=data;
        console.log(data);
      }
    });
  }

  getCurrentEnrollments(){
    const url = `${UsersService.enrollmentUrl}${UsersService.currentEnrollmentsApi}?id=${this.studentId}`;
    this.http.get<PastCurrentEnrollmentsReponseBody[]>(url).subscribe((data) => {
      if (data != null) {
        this.currentEnrollments=data;
        console.log(data);
      }
    });
  }
  decide(courseId:any){
    // let body = {
    //   studentId: this.studentId,
    //   course:courseId,
    //   rating:this.rating,
    //   review:this.review
    //  };
     const url = `${UsersService.courseUrl}${UsersService.makeReviewApi}?course=${courseId}&student=${this.studentId}&rate=${this.rating}&feedback=${this.review}`;
     console.log(courseId);
     console.log(this.studentId);
     console.log(this.rating);
     console.log(this.review);
     console.log(url);
    this.http.post<boolean>(url,null).subscribe((data)=>{
      if(data!=null)
      {
        console.log(data);
      }
      }
    );
  }

}
