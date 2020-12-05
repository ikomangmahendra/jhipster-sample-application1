import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBarang } from 'app/shared/model/barang.model';
import { BarangService } from './barang.service';

@Component({
  templateUrl: './barang-delete-dialog.component.html',
})
export class BarangDeleteDialogComponent {
  barang?: IBarang;

  constructor(protected barangService: BarangService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.barangService.delete(id).subscribe(() => {
      this.eventManager.broadcast('barangListModification');
      this.activeModal.close();
    });
  }
}
