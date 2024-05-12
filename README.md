# EJBs

## Admin Management DONE

- Type: Stateless Session Bean
- Name: `AdminManagementBean`
- Description:
  - Methods:
    - `trackPlatformUsage()`: Track platform usage by students and instructors.

## Instructor Management DONE

- Type: Stateless Session Bean
- Name: `InstructorManagementBean`
- Description:
  - Methods:
    - `acceptRejectEnrollment(enrollmentId, decision)`: Accept or reject student enrollments, and send notification to student.

## Student Management DONE

- Type: Stateless Session Bean
- Name: `StudentManagementBean`
- Description:
  - Methods:
    - `viewEnrollments()`: View current and past course enrollments.
    - `enrollCourse(courseId)`: Enroll in a course.
    - `cancelEnrollment(enrollmentId)`: Cancel course enrollment.
    - `getCourseUpdates()`: Get notified for course enrollment updates.

# Microservices

## Course Management Microservice DONE

- Type: RESTful Microservice
- Name: `CourseManagementService`
- Description:
  - API Endpoints:
    - `POST /courses`: Create a new course.
    - `GET /courses/{courseId}`: Get detailed information about a course.
    - `PUT /courses/{courseId}`: Update course details.
    - `DELETE /courses/{courseId}`: Remove a course.
    - `GET /courses/search?name={name}&category={category}&sort={sortCriteria}`: Search courses by name, category, or sort by ratings.
    - `POST /review`: Create a new review.
    - `GET /reviews`: get course reviews.

## User Management Microservice DONE

- Type: RESTful Microservice
- Name: `UserManagementService`
- Description:
  - API Endpoints:
    - `POST /users/register`: Register a new user account (admin, instructor, or student).
    - `GET /users/{userId}`: Get user account details.
    - `PUT /users/{userId}`: Update user account details.
    - `DELETE /users/{userId}`: Remove a user account.
    - `GET /users`: Get all user accounts
    - `GET /usertype`: Get the type of a user
