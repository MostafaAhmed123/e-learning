import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './screens/login/login.component';
import { SignUpComponent } from './screens/sign-up/sign-up.component';
import { InstructorComponent } from './screens/instructor/instructor.component';
import { StudentComponent } from './screens/student/student.component';
import { AdminComponent } from './screens/admin/admin.component';


const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  {path:'instructor',component:InstructorComponent},
  {path:'student',component:StudentComponent},
  {path:'admin',component:AdminComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
