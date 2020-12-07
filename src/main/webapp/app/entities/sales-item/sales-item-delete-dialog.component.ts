import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalesItem } from 'app/shared/model/sales-item.model';
import { SalesItemService } from './sales-item.service';

@Component({
  templateUrl: './sales-item-delete-dialog.component.html',
})
export class SalesItemDeleteDialogComponent {
  salesItem?: ISalesItem;

  constructor(protected salesItemService: SalesItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('salesItemListModification');
      this.activeModal.close();
    });
  }
}
