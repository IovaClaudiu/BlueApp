import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService, UserService, AlertService } from '../_services';
import { Subject } from 'rxjs';

@Component({ selector: 'app-register', templateUrl: 'register.component.html' })
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;
  isModal = false;
  registerSubject = new Subject<boolean>();

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService
  ) {
    if (this.isModal === false) {
      if (this.authenticationService.currentUserValue) {
        this.router.navigate(['/']);
      }
    }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  onCancel(): void {
    if (this.isModal === false) {
      this.router.navigate(['/login']);
    } else {
      this.registerSubject.next(false);
    }
  }

  onSubmit(): void {
    this.submitted = true;
    this.alertService.clear();
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.userService
      .register(this.registerForm.value)
      .pipe(first())
      .subscribe(
        (data) => {
          if (this.isModal === false) {
            this.alertService.success('Registration successful', true);
            this.router.navigate(['/login']);
          } else {
            this.registerSubject.next(true);
            this.userService.changeSubject.next(data);
          }
        },
        (error) => {
          if (this.isModal === false) {
            this.alertService.error(error.error);
          } else {
            alert(error.error);
          }
          this.loading = false;
        }
      );
  }
}
