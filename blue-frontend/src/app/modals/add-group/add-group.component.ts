import { Group } from './../../_models/group';
import { first, catchError } from 'rxjs/operators';
import { GroupsService } from './../../_services/groups.service';
import { Component, OnInit, Input } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { group } from '@angular/animations';

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
    this.bsModalRef = this.modalService.show(ModalContentComponent, {
      initialState,
    });
  }
}

/* This is a component which we pass in modal*/
@Component({
  selector: 'modal-content',
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
      <label for="groupName">Group Name</label>
      <input #groupName class="form-control" id="groupName" />
      <button
        class="mt-3 btn btn-success"
        (click)="onAddGroup(groupName.value)"
      >
        Add Group
      </button>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" (click)="bsModalRef.hide()">
        {{ closeBtnName }}
      </button>
    </div>
  `,
})
export class ModalContentComponent implements OnInit {
  title: string;
  closeBtnName: string;

  constructor(
    public bsModalRef: BsModalRef,
    private grpService: GroupsService
  ) {}

  ngOnInit() {}

  onAddGroup(groupName: string) {
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
