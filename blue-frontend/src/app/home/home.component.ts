import { Component, OnInit, OnDestroy } from '@angular/core';
import { first, timeout } from 'rxjs/operators';
import { User } from '../_models';
import { AuthenticationService, UserService, AlertService } from '../_services';
import { Subscription } from 'rxjs';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit, OnDestroy {
  currentUser: User;
  users: Array<User> = [];
  private changeSubscription: Subscription;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService
  ) {
    this.currentUser = this.authenticationService.currentUserValue;
    this.changeSubscription = this.userService.changeSubject.subscribe(
      (data) => {
        this.loadAllUsers();
      }
    );
  }

  ngOnInit(): void {
    this.loadAllUsers();
  }

  ngOnDestroy(): void {
    this.changeSubscription.unsubscribe();
  }

  private loadAllUsers(): void {
    this.userService
      .getAll()
      .pipe(first())
      .subscribe((users) => {
        this.users = users;
      });
  }

  deleteUser(email: string, role: string): void {
    if (email === this.currentUser.email || role === 'ROLE_ADMIN') {
      return;
    }
    if (
      confirm(
        'Are you sure you want to delete the user with email: ' + email + ' ?'
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

  onAddUser(): void {
    console.log('TODO');
  }
}
