import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication1SharedModule } from 'app/shared/shared.module';
import { PurchaseItemComponent } from './purchase-item.component';
import { PurchaseItemDetailComponent } from './purchase-item-detail.component';
import { PurchaseItemUpdateComponent } from './purchase-item-update.component';
import { PurchaseItemDeleteDialogComponent } from './purchase-item-delete-dialog.component';
import { purchaseItemRoute } from './purchase-item.route';

@NgModule({
  imports: [JhipsterSampleApplication1SharedModule, RouterModule.forChild(purchaseItemRoute)],
  declarations: [PurchaseItemComponent, PurchaseItemDetailComponent, PurchaseItemUpdateComponent, PurchaseItemDeleteDialogComponent],
  entryComponents: [PurchaseItemDeleteDialogComponent],
})
export class JhipsterSampleApplication1PurchaseItemModule {}
