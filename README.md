# BlueApp

## Requirements

- build a user management system that would allow you to create/delete users
- users in the system should have the following details: id, first name, last name, email (unique)
- an admin user should exist by default
- any user can add/delete existing users, nor the admin user and the user itself
- a login screen should be presented to access the main page
- main page should allow add/delete functionality, no modify/update functionality is needed
- login should be persistent; once login, you are presented w/ the main page 8 logout functionality should exist; once logged out, when trying to access the main page, a login page should be presented
- group functionality should be present; admin can add/delete groups; admin can assign users to groups; separate page should be presented for the group feature
- users can be part of any single group; they can also be part of no group

## Frameworks

### Backend

REST service for manipulating the users.

- Java 11
- Spring Boot + Spring Data + Spring Security + JWT
- Hibernate (JPA)
- MySQL

### Frontend

SPA - Single Page Application

- Angular 8
- Ngx Bootstrap
