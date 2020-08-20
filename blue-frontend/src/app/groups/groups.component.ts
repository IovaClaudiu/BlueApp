import { Groups as Group } from './../_models/groups';
import { AlertService } from './../_services/alert.service';
import { first } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { User } from '../_models';
import { GroupsService, AuthenticationService } from '../_services';

@Component({ templateUrl: 'groups.component.html' })
export class GroupsComponent implements OnInit {
  currentUser: User;
  groups: Group[];

  constructor(
    private groupsService: GroupsService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authenticationService.currentUserValue;
    this.loadAllGroups();
  }

  onAdd() {
    console.log('press');
  }

  private loadAllGroups() {
    this.groupsService
      .getAll()
      .pipe(first())
      .subscribe(
        (groups) => {
          this.groups = groups;
        },
        (error) => {
          this.alertService.error(error.error);
          setTimeout(() => {
            this.alertService.clear();
          }, 4000);
        }
      );
  }
}
