import { Component, OnInit} from '@angular/core';
import { Router} from '@angular/router';
import {
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
import { LoginResponseBody } from 'src/app/models/login.response.body';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(private router: Router, private http: HttpClient) {}
  ngOnInit(): void {}

  email: string = '';
  password: string = '';

  navToSignUp() {
    this.router.navigateByUrl('/signup');
  }

  login() {


    if (this.email.trim().length === 0) {
      alert('Email is required');
    } else if (this.password.trim().length === 0) {
      alert('Password is required');
    } else {
      this.http
        .post<LoginResponseBody>(
          UsersService.accountUrl + UsersService.loginEndPoint,
          {
            email: this.email,
            password: this.password,
          }
        )
        .subscribe(
          (data) => {
            console.log(data);

            if (data != null && data.role == 'instructor') {
              this.router.navigateByUrl('/instructor');
                localStorage.setItem('instructorName',`${data.name}`);
                localStorage.setItem('instructorId',`${data.id}`);
            } else {
              this.router.navigateByUrl('/student');
              localStorage.setItem('studentName',`${data.name}`);
              localStorage.setItem('studentId',`${data.id}`);
            }
          },
          (error: HttpErrorResponse) => {
            if (error != null) {
              alert('Something Went Wrong');
            }
            console.log(error.error);
          }
        );
    }
  }
}
