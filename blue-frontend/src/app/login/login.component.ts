import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService, AlertService } from '../_services';

@Component({ templateUrl: 'login.component.html' })
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;
  returnUrl: string;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    this.alertService.clear();
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService
      .login(this.f.email.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        (data) => {
          this.router.navigate(['/']);
        },
        (error) => {
          this.alertService.error(error.error);
          this.loading = false;
        }
      );
  }
}
