import { CheckUserRole } from './../../../_helpers/checkRole';
import { first } from 'rxjs/operators';
import { GroupsService } from './../../../_services/groups.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Component } from '@angular/core';
import { Group } from '../../../_models/group';

@Component({
  templateUrl: 'add-group-dialog.html',
})
export class ModalAddGroupComponent {
  title: string;
  closeBtnName: string;

  constructor(
    public bsModalRef: BsModalRef,
    private grpService: GroupsService,
    private checkRole: CheckUserRole
  ) {}

  onAddGroup(groupName: string): void {
    if (this.checkRole.verifyHasRights()) {
      this.grpService
        .addGroup({ groupName: groupName })
        .pipe(first())
        .subscribe(
          (data: Group) => {
            this.grpService.addNewGroupSubscription.next(data);
          },
          (err) => {
            alert(err);
          }
        );
      this.bsModalRef.hide();
    }
  }
}
