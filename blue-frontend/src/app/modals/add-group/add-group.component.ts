import { ModalAddGroupComponent } from './add-group-dialog/add-group-dialog';
import { Component, Input } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.component.html',
  styleUrls: ['./add-group.component.css'],
})
export class AddGroupComponent {
  bsModalRef: BsModalRef;
  @Input() isDisabled: boolean;
  constructor(private modalService: BsModalService) {}

  addGroup() {
    const initialState = {
      title: 'Add group',
      closeBtnName: 'Close',
    };
    this.bsModalRef = this.modalService.show(ModalAddGroupComponent, {
      initialState,
    });
  }
}
