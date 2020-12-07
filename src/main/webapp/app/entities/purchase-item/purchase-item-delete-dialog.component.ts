import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseItem } from 'app/shared/model/purchase-item.model';
import { PurchaseItemService } from './purchase-item.service';

@Component({
  templateUrl: './purchase-item-delete-dialog.component.html',
})
export class PurchaseItemDeleteDialogComponent {
  purchaseItem?: IPurchaseItem;

  constructor(
    protected purchaseItemService: PurchaseItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.purchaseItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('purchaseItemListModification');
      this.activeModal.close();
    });
  }
}
