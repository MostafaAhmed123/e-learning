import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UsersService } from 'src/app/services/users.service';
import { SignUpResponseBody } from 'src/app/models/signup.response.body';
import { Output, EventEmitter, OnInit  } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { NgIf } from '@angular/common';


@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css'],
})
export class SignUpComponent implements OnInit {
  @Output() formToggled = new EventEmitter<boolean>();
  constructor(private router: Router, private http: HttpClient,private formBuilder: FormBuilder) {
    this.signupForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      affiliation: ['', Validators.required],
      role: ['student', Validators.required],
      experience: [''],
      bio:['']
    });
   
  }
  onSubmit() {
    // console.log(this.signupForm.value);
    // console.log(this.signupForm.get('email'));
    // console.log(this.signupForm.get('name'));
    // console.log(this.signupForm.get('password'));
    // console.log(this.signupForm.get('affiliation'));
    // console.log(this.signupForm.get('role'));
    // console.log(this.signupForm.get('bio'));
    // console.log(this.signupForm.get('experience'));
   
  }
  
  ngOnInit(): void {
    this.initForm();
  }
  private initForm(): void {
   
  }
  role:string="student";
  signupForm: FormGroup;
  email: string = '';
  name: string = '';
  affilation: String = '';
  password: string = '';
  exp:number=0;
  bio:String='';

 

  
  navToSignIn() {
    this.router.navigateByUrl('/login');
  }
// add more check
  signUp() {

    const name = this.signupForm.get('name')!.value;
    const email = this.signupForm.get('email')!.value;
    const password = this.signupForm.get('password')!.value;
    const affiliation = this.signupForm.get('affiliation')!.value;
    const role = this.signupForm.get('role')!.value;
    const experience = this.signupForm.get('experience')!.value;
    const bio = this.signupForm.get('bio')!.value;
  
    console.log("Name:", name);
    console.log("Email:", email);
    console.log("Password:", password);
    console.log("Affiliation:", affiliation);
    console.log("Role:", role);
    console.log("Experience:", experience);
    console.log("bio:", bio);
     
    
     
      this.http
        .post<SignUpResponseBody>(
          UsersService.accountUrl + UsersService.signupEndPoint,
          {
            name: name,
            email: email,
            password: password,
            affiliation: affiliation,
            role: role,
            years_of_experience:experience,
            bio:bio,
          }
        )
        .subscribe(
          (data) => {
            if (data != null) {
              this.router.navigateByUrl('/login');
            }
          },
          (error: HttpErrorResponse) => {
            if (error != null) {
              // make sure if its fine
              alert(error);
            }
            console.log(error.error);
          }
        );
    }
  }

