import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGudang } from 'app/shared/model/gudang.model';
import { GudangService } from './gudang.service';

@Component({
  templateUrl: './gudang-delete-dialog.component.html',
})
export class GudangDeleteDialogComponent {
  gudang?: IGudang;

  constructor(protected gudangService: GudangService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gudangService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gudangListModification');
      this.activeModal.close();
    });
  }
}
