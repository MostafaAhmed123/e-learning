import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './screens/login/login.component';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SignUpComponent } from './screens/sign-up/sign-up.component';
import { HttpClientModule } from '@angular/common/http';
import { InstructorComponent } from './screens/instructor/instructor.component';
import { StudentComponent } from './screens/student/student.component';
import { Ng2SearchPipe } from 'ng2-search-filter';
import { FilterPipe } from './filter.pipe';
import { AdminComponent } from './screens/admin/admin.component';



@NgModule({
  declarations: [
    LoginComponent,
    AppComponent,
    SignUpComponent,
    InstructorComponent,
    StudentComponent,
    FilterPipe,
    AdminComponent,

  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule,ReactiveFormsModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
