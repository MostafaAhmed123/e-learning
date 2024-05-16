import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';
import { HttpClient,HttpErrorResponse,HttpHeaders,HttpParams } from '@angular/common/http';
import { GetAllUsersAdminResponseBody } from 'src/app/models/getAllUsersAdmin.response.body';
import { EditedUserAdminBody } from 'src/app/models/editedUserAdmin.body';




@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  constructor( private router: Router, private http: HttpClient) { }

  // users = [{name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  // {name:'john',email:'amr@gmail.com',affiliation:'web',role:'instructor',experience:'1'},
  // {name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  // {name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  // ];

 users: Array<GetAllUsersAdminResponseBody>=[];
 editedUser: any = {};
 editedCourse: any = {};
  editedSucess:string="";
  courses = [{name: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35,status:"pending",},
  {name: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35,status:"pending",},
  {name: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35,status:"pending",},
  {name: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35,status:"pending",},
  ];

  showEditForm: boolean = false;
  showCourseEditForm: boolean = false;

  userId: any;

  ngOnInit(): void {
    // this.loadUsers();
    // this.loadCourses();
    this.getUsers();


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

cancelEditv2(){
  this.showCourseEditForm=false;
}
  cancelEdit() {
    this.showEditForm = false;
  }

  submitEdit() {
    // Implement save changes functionality
    console.log("Edited User:", this.editedUser);
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
  editCourseConfirm(){


  }
  editCourse(course: any) {
    // Implement approve course functionality
    this.editCourseConfirm();

    this.showCourseEditForm=true;
  }

  removeCourse(course: any) {
    // Implement reject course functionality
  }
}
