import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';

@Component({
  templateUrl: './confirm-dialog.component.html',
})
export class ConforimWindowDialog implements OnInit {
  message: string;
  public onClose: Subject<boolean>;

  constructor(private modalRef: BsModalRef) {}

  ngOnInit(): void {
    this.onClose = new Subject();
  }

  confirm(): void {
    this.onClose.next(true);
    this.modalRef.hide();
  }

  decline(): void {
    this.onClose.next(false);
    this.modalRef.hide();
  }
}
