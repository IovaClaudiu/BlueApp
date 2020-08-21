import { ConforimWindowDialog } from 'src/app/modals/confirm-dialog/confirm-dialog.component';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { first, timeout } from 'rxjs/operators';
import { User } from '../_models';
import { AuthenticationService, UserService, AlertService } from '../_services';
import { Subscription } from 'rxjs';
import { RegisterComponent } from '../register';

@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit, OnDestroy {
  currentUser: User;
  users: Array<User> = [];
  modalRef: BsModalRef;

  private changeSubscription: Subscription;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService,
    private modalService: BsModalService
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

  deleteUser(email: string): void {
    {
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

  openConfirmationDialog(email: string, role: string): void {
    if (email === this.currentUser.email || role === 'ROLE_ADMIN') {
      return;
    }
    this.modalRef = this.modalService.show(ConforimWindowDialog, {
      class: 'modal-sm',
      initialState: {
        message:
          'Are you sure you want to delete the user with email: ' +
          email +
          ' ?',
      },
    });
    this.modalRef.content.onClose.subscribe((result: boolean) => {
      if (result) {
        this.deleteUser(email);
      }
    });
  }

  onAddUser(): void {
    this.modalRef = this.modalService.show(RegisterComponent, {
      initialState: {
        isModal: true,
      },
    });
    this.modalRef.content.registerSubject.subscribe((data: boolean) => {
      this.modalRef.hide();
    });
  }
}
