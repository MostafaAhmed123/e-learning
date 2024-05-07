# EJBs

## Admin Management

- Type: Stateless Session Bean
- Name: `AdminManagementBean`
- Description:
  - Methods:
    - `viewUserAccounts()`: View and manage user accounts, including students and instructors.
    - `reviewCourseContent(courseId)`: Review course content before it's published.
    - `editCourse(courseDetails)`: Edit course details.
    - `removeCourse(courseId)`: Remove a course.
    - `trackPlatformUsage()`: Track platform usage by students and instructors.

## Instructor Management

- Type: Stateless Session Bean
- Name: `InstructorManagementBean`
- Description:
  - Methods:
    - `acceptRejectEnrollment(enrollmentId, decision)`: Accept or reject student enrollments.

## Student Management

- Type: Stateless Session Bean
- Name: `StudentManagementBean`
- Description:
  - Methods:
    - `viewEnrollments()`: View current and past course enrollments.
    - `enrollCourse(courseId)`: Enroll in a course.
    - `cancelEnrollment(enrollmentId)`: Cancel course enrollment.
    - `makeReviewAndRating(courseId, reviewRating)`: Make a review and rating for a course.
    - `getCourseUpdates()`: Get notified for course enrollment updates.

## Enrollment and Notification Management

- Type: Stateless Session Bean
- Name: `EnrollmentNotificationBean`
- Description:
  - Methods:
    - `enrollCourse(courseId)`: Enroll in a course.
    - `cancelEnrollment(enrollmentId)`: Cancel course enrollment.
    - `sendNotifications()`: Send notifications for course enrollment updates.

# Microservices

## Course Management Microservice

- Type: RESTful Microservice
- Name: `CourseManagementService`
- Description:
  - API Endpoints:
    - `POST /courses`: Create a new course.
    - `GET /courses/{courseId}`: Get detailed information about a course.
    - `PUT /courses/{courseId}`: Update course details.
    - `DELETE /courses/{courseId}`: Remove a course.
    - `GET /courses/search?name={name}&category={category}&sort={sortCriteria}`: Search courses by name, category, or sort by ratings.

## User Management Microservice

- Type: RESTful Microservice
- Name: `UserManagementService`
- Description:
  - API Endpoints:
    - `POST /users/register`: Register a new user account (admin, instructor, or student).
    - `GET /users/{userId}`: Get user account details.
    - `PUT /users/{userId}`: Update user account details.
    - `DELETE /users/{userId}`: Remove a user account.
    - `GET /users`: Get all user accounts
