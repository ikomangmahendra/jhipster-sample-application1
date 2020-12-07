import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatalogItem } from 'app/shared/model/catalog-item.model';
import { CatalogItemService } from './catalog-item.service';

@Component({
  templateUrl: './catalog-item-delete-dialog.component.html',
})
export class CatalogItemDeleteDialogComponent {
  catalogItem?: ICatalogItem;

  constructor(
    protected catalogItemService: CatalogItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catalogItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catalogItemListModification');
      this.activeModal.close();
    });
  }
}
