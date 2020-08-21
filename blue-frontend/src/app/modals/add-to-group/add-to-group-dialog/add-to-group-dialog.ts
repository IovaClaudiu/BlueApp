import { first } from 'rxjs/operators';
import { UserService } from './../../../_services/user.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Group } from './../../../_models/group';
import { Component } from '@angular/core';

@Component({
  templateUrl: 'add-to-group-dialog.html',
})
export class ModalAddToGroupComponent {
  title: string;
  closeBtnName: string;
  groups: Group[] = [];
  selectedOption: string;
  email: string;

  constructor(public bsModalRef: BsModalRef, private srvUser: UserService) {}

  onSave(): void {
    this.srvUser
      .setUserGroup(this.email, { groupName: this.selectedOption })
      .pipe(first())
      .subscribe((data) => {
        this.srvUser.changeSubject.next();
      });
    this.bsModalRef.hide();
  }
}
