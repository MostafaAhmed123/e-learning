import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';
import { HttpClient,HttpErrorResponse,HttpHeaders,HttpParams } from '@angular/common/http';
import { GetAllUsersAdminResponseBody } from 'src/app/models/getAllUsersAdmin.response.body';
import { EditedUserAdminBody } from 'src/app/models/editedUserAdmin.body';
import { GetAllCoursesInstructorResponseBody } from 'src/app/models/getAllCoursesInstructor.response.body';





@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  constructor( private router: Router, private http: HttpClient) { }
  static adminId = 5;
  // users = [{name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  // {name:'john',email:'amr@gmail.com',affiliation:'web',role:'instructor',experience:'1'},
  // {name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  // {name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  // ];

 users: Array<GetAllUsersAdminResponseBody>=[];
 courses:Array<GetAllCoursesInstructorResponseBody>=[];
 editedUser: any = {};
 editedCourse: any = {};
  editedSucess:string="";


  showEditForm: boolean = false;
  showCourseEditForm: boolean = false;

  userId: any;

  ngOnInit(): void {
    // this.loadUsers();
    // this.loadCourses();
    this.getUsers();
    this.getAllCourses();

  }

  // loadUsers() {
  //   this.userService.getUsers().subscribe((users: User[]) => {
  //     this.users = users;
  //   });
  // }

 /*
  user_data = {
            "id": user.id,
            "name": user.name,
            "email": user.email,
            "role": user.role,
            "affiliation": user.affiliation,
            "years_of_experience": user.years_of_experience,
            "bio": user.bio,
        }
*/


getUsers(){
this.http.get<GetAllUsersAdminResponseBody[]>(UsersService.accountUrl+UsersService.getAllUsersAdminEndPoint).subscribe((data)=>{
if(data!=null){
  this.users=data;
}
});
}
// allCoursesAdminEndPoint
getAllCourses(){
  this.http.get<GetAllUsersAdminResponseBody[]>(UsersService.courseUrl+UsersService.allCoursesAdminEndPoint).subscribe((data)=>{
    if(data!=null){
      this.courses=data;
      console.log(data);
    }
    });
}

  cancelEdit() {
    this.showEditForm = false;
  }


  /*
  {
    "name": "Updated Name",
    "email": "updated_email@example.com",
    "password": "newpassword",
    "role": "updated role",
    "affiliation": "updated affiliation",
    "years_of_experience": 3,
    "bio": "updated bio"
}
  */

  editUser(user: any) {
    //change the user list here and then call it ngonit /users/4
    this.editUserConfirm();
    this.editedUser = { ...user };
    this.showEditForm = true;
  }

  editUserConfirm() {
    const index = this.users.findIndex(user => user?.id === this.editedUser?.id);
    console.log(this.editedUser);
    if (index !== -1) {
      this.users[index] = { ...this.editedUser };
    }
 let body={
  name:this.editedUser.name,
  email:this.editedUser.email,
  password:this.editedUser.password,
  role:this.editedUser.role,
  affiliation:this.editedUser.affiliation,
  years_of_experience:this.editedUser.years_of_experience,
  bio:this.editedUser.bio
 }
    // Get the id from the edited user
    const userId = this.editedUser?.id;
    if (!userId) {
      // Handle error condition when id is not available
      return;
    }
    const url = `${UsersService.accountUrl}${UsersService.editUsersAdminEndPoint}?id=${userId}`;
    this.http.put<string>(url,body).subscribe((data) => {
      if (data != null) {
        location.reload();

        console.log(data);
      }
    });
  }


  deleteUser(user: any) {
   const userId = user.id;
    const url = `${UsersService.accountUrl}${UsersService.editUsersAdminEndPoint}?id=${userId}`;
    this.http.delete<string>(url).subscribe((data) => {
      if (data != null) {
        location.reload();
        console.log(data);
      }
    });  }


  // loadCourses() {
  //   this.courseService.getCoursesForReview().subscribe((courses: Course[]) => {
  //     this.courses = courses;
  //   });
  // }

  //editCoursesAdminEndPoint
  editCourseConfirm(){
    const index = this.courses.findIndex(course => course?.courseId === this.editedCourse?.courseId);
    console.log(this.editedCourse);
    if (index !== -1) {
      this.courses[index] = { ...this.editedCourse };
    }

 let body={
  courseId:this.editedCourse.courseId,
  name:this.editedCourse.name,
  duration:this.editedCourse.duration,
  content:this.editedCourse.content,
  capacity:this.editedCourse.capacity,
  approvedByAdmin:this.editedCourse.approvedByAdmin,
  popularity:this.editedCourse.populairty,
  instructorId:this.editedCourse.instructorId,
  status:this.editedCourse.status,
  category:this.editedCourse.category,
 }
    // Get the id from the edited user
    const id = this.editedCourse?.courseId;
    if (!id) {
      // Handle error condition when id is not available
      return;
    }
    const url = `${UsersService.courseUrl}${UsersService.editCoursesAdminEndPoint}?id=${AdminComponent.adminId}`;
    this.http.put<string>(url,body).subscribe((data) => {
      if (data != null) {
        location.reload();
        console.log(data);
      }
    });

  }
  cancelCourseEdit(){
    this.showCourseEditForm=false;
  }
  editCourseCheck(course: any) :void{
    // Implement approve course functionality
    this.editCourseConfirm();
    this.editedCourse = { ...course };
    this.showCourseEditForm=true;
  }

  removeCourse(course: any) {
    const id = course.courseId;
    const url = `${UsersService.courseUrl}${UsersService.deleteCoursesAdminEndPoint}?course=${id}&id=${AdminComponent.adminId}`;
    this.http.delete<string>(url).subscribe((data) => {
      if (data != null) {
        location.reload();
        console.log(data);
      }
    });
  }
}
