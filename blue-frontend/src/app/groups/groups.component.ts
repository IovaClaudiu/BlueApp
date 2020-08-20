import { Group } from '../_models/group';
import { AlertService } from './../_services/alert.service';
import { first } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { User } from '../_models';
import { GroupsService, AuthenticationService } from '../_services';
import { Subscription } from 'rxjs';

@Component({ templateUrl: 'groups.component.html' })
export class GroupsComponent implements OnInit {
  currentUser: User;
  groups: Group[];
  private addGroupSubscription: Subscription;
  private removeGroupSubscription: Subscription;

  constructor(
    private groupsService: GroupsService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authenticationService.currentUserValue;
    this.loadAllGroups();

    this.addGroupSubscription = this.groupsService.addNewGroupSubscription.subscribe(
      (data: Group) => {
        this.groups.push(data);
      }
    );
    this.removeGroupSubscription = this.groupsService.removeGroupSubscription.subscribe(
      (data: Group) => {
        this.groups = this.groups.filter((g) => {
          return g.groupName !== data.groupName;
        });
      }
    );
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
