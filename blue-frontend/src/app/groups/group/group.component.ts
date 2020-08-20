import { GroupsService } from './../../_services/groups.service';
import { Group } from './../../_models/group';
import { AuthenticationService } from './../../_services/authentication.service';
import { Component, Input } from '@angular/core';
import { User } from 'src/app/_models';
import { first } from 'rxjs/operators';

@Component({
  templateUrl: './group.component.html',
  selector: 'app-group-component',
})
export class GroupComponent {
  @Input() group: Group;
  currentUser: User;

  constructor(
    private authService: AuthenticationService,
    private groupService: GroupsService
  ) {
    this.currentUser = this.authService.currentUserValue;
  }

  onDelete(): void {
    if (
      confirm(
        'Are you sure you want to delete the following group: ' +
          this.group.groupName
      )
    ) {
      this.groupService
        .removeGroup(this.group.groupName)
        .pipe(first())
        .subscribe(
          (data) => {
            this.groupService.removeGroupSubscription.next(this.group);
          },
          (error) => {
            alert(error.error);
          }
        );
    }
  }
}
