import { ModalAddToGroupComponent } from './add-to-group-dialog/add-to-group-dialog';
import { Group } from './../../_models/group';
import { GroupsService } from './../../_services/groups.service';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Component, OnInit, Input } from '@angular/core';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-add-to-group',
  templateUrl: './add-to-group.component.html',
  styleUrls: ['./add-to-group.component.css'],
})
export class AddToGroupComponent implements OnInit {
  bsModalRef: BsModalRef;
  groups: Group[];
  @Input() isDisabled: boolean;
  @Input() userEmail: String;
  constructor(
    private modalService: BsModalService,
    private grpService: GroupsService
  ) {}

  ngOnInit(): void {
    this.grpService
      .getGroups()
      .pipe(first())
      .subscribe((data) => {
        this.groups = data;
      });
  }

  addToGroup(): void {
    const initialState = {
      title: 'Select group',
      closeBtnName: 'Close',
      groups: this.groups,
      email: this.userEmail,
    };
    this.bsModalRef = this.modalService.show(ModalAddToGroupComponent, {
      initialState,
    });
  }
}
