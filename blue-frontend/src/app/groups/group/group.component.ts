import { CheckUserRole } from './../../_helpers/checkRole';
import { GroupsService } from './../../_services/groups.service';
import { Group } from './../../_models/group';
import { AuthenticationService } from './../../_services/authentication.service';
import { Component, Input } from '@angular/core';
import { User } from 'src/app/_models';
import { first } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ConforimWindowDialog } from 'src/app/modals/confirm-dialog/confirm-dialog.component';

@Component({
  templateUrl: './group.component.html',
  selector: 'app-group-component',
})
export class GroupComponent {
  @Input() group: Group;
  currentUser: User;
  modalRef: BsModalRef;

  constructor(
    private authService: AuthenticationService,
    private groupService: GroupsService,
    private checkRole: CheckUserRole,
    private modalService: BsModalService
  ) {
    this.currentUser = this.authService.currentUserValue;
  }

  onDelete(): void {
    if (this.checkRole.verifyHasRights()) {
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

  openConfirmationDialog(): void {
    this.modalRef = this.modalService.show(ConforimWindowDialog, {
      class: 'modal-sm',
      initialState: {
        message:
          'Are you sure you want to delete the group: ' +
          this.group.groupName +
          ' ?',
      },
    });
    this.modalRef.content.onClose.subscribe((result: boolean) => {
      if (result) {
        this.onDelete();
      }
    });
  }
}
