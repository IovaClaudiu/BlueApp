import { Group } from './../../_models/group';
import { GroupsService } from './../../_services/groups.service';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Component, OnInit, NgModule, Input } from '@angular/core';
import { first } from 'rxjs/operators';
import { UserService } from 'src/app/_services';

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

/* This is a component which we pass in modal*/
@Component({
  template: `
    <div class="modal-header">
      <h4 class="modal-title pull-left">{{ title }}</h4>
      <button
        type="button"
        class="close pull-right"
        aria-label="Close"
        (click)="bsModalRef.hide()"
      >
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <div class="form-group">
        <label for="groups">Select group</label>
        <select class="form-control" id="groups" [(ngModel)]="selectedOption">
          <option *ngFor="let g of groups">{{ g.groupName }}</option>
        </select>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-success" (click)="onSave()">
        Apply
      </button>
      <button type="button" class="btn btn-default" (click)="bsModalRef.hide()">
        {{ closeBtnName }}
      </button>
    </div>
  `,
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
