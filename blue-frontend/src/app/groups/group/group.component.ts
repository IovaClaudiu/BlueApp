import { AuthenticationService } from './../../_services/authentication.service';
import { Groups } from './../../_models/groups';
import { Component, Input } from '@angular/core';
import { User } from 'src/app/_models';

@Component({
  templateUrl: './group.component.html',
  selector: 'app-group-component',
})
export class GroupComponent {
  @Input() group: Groups;
  currentUser: User;

  constructor(private authService: AuthenticationService) {
    this.currentUser = authService.currentUserValue;
  }
}
