import { User } from "@/_models";
import { AuthenticationService } from "./../../_services/authentication.service";
import { Groups } from "./../../_models/groups";
import { Component, Input } from "@angular/core";

@Component({
  templateUrl: "./group.component.html",
  selector: "app-group-component",
})
export class GroupComponent {
  @Input() group: Groups;
  currentUser: User;

  constructor(private authService: AuthenticationService) {
    this.currentUser = authService.currentUserValue;
  }
}
