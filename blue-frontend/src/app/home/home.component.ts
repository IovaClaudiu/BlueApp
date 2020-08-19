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
    if (
      confirm(
        "Are you sure you want to delete the user with email: " + email + " ?"
      )
    ) {
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
}
