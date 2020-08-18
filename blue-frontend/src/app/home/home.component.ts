import { Component, OnInit } from "@angular/core";
import { first, timeout } from "rxjs/operators";

import { User } from "@/_models";
import { UserService, AuthenticationService, AlertService } from "@/_services";

@Component({ templateUrl: "home.component.html" })
export class HomeComponent implements OnInit {
  currentUser: User;
  users = [];

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService
  ) {
    this.currentUser = this.authenticationService.currentUserValue;
  }

  ngOnInit() {
    this.loadAllUsers();
  }

  private loadAllUsers() {
    this.userService
      .getAll()
      .pipe(first())
      .subscribe((users) => (this.users = users));
  }

  deleteUser(email: string, role: string) {
    if (this.validateUser(email, role)) {
      this.userService
        .delete(email)
        .pipe(first())
        .subscribe(
          () => this.loadAllUsers(),
          (error) => {
            this.alertService.error(error.error);
            setTimeout(() => {
              this.alertService.clear();
            }, 4000);
            this.loadAllUsers();
          }
        );
    }
  }

  private validateUser(email: string, role: string): boolean {
    if (email === this.currentUser.email || role === "ROLE_ADMIN") {
      this.alertService.error(
        "The selected user with email: " + email + " cannot be deleted!"
      );
      setTimeout(() => {
        this.alertService.clear();
      }, 4000);
      return false;
    } else {
      const response = confirm(
        "Are you sure you want to delete the user with email: " + email + " ?"
      );
      return response;
    }
  }
}
