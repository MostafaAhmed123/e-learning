import { Component } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

  users = [{name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  {name:'john',email:'amr@gmail.com',affiliation:'web',role:'instructor',experience:'1'},
  {name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  {name:'john',email:'amr@gmail.com',affiliation:'web',role:'student'},
  ];

  courses = [{name: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35,status:"pending",},
  {name: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35,status:"pending",},
  {name: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35,status:"pending",},
  {name: 'Introduction to Programming', category: 'Programming', duration: '6 weeks', rating: 4.5, capacity: 50, enrolledStudents: 35,status:"pending",},
  ];

  showEditForm: boolean = false;
  editedUser: any;
  

  ngOnInit(): void {
    // this.loadUsers();
    // this.loadCourses();

  }

  // loadUsers() {
  //   this.userService.getUsers().subscribe((users: User[]) => {
  //     this.users = users;
  //   });
  // }
  cancelEdit() {
    this.showEditForm = false;
  }

  submitEdit() {
    // Implement save changes functionality
    console.log("Edited User:", this.editedUser);
    this.showEditForm = false;
  }
  editUser(user: any) {
    //change the user list here and then call it ngonit
    this.editedUser = { ...user }; 
    this.showEditForm = true;
  }

  deleteUser(user: any) {
    // Implement delete functionality
  }


  // loadCourses() {
  //   this.courseService.getCoursesForReview().subscribe((courses: Course[]) => {
  //     this.courses = courses;
  //   });
  // }

  editCourse(course: any) {
    // Implement approve course functionality
  }

  removeCourse(course: any) {
    // Implement reject course functionality
  }
}
