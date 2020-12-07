import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication1SharedModule } from 'app/shared/shared.module';
import { PurchaseComponent } from './purchase.component';
import { PurchaseDetailComponent } from './purchase-detail.component';
import { PurchaseUpdateComponent } from './purchase-update.component';
import { PurchaseDeleteDialogComponent } from './purchase-delete-dialog.component';
import { purchaseRoute } from './purchase.route';

@NgModule({
  imports: [JhipsterSampleApplication1SharedModule, RouterModule.forChild(purchaseRoute)],
  declarations: [PurchaseComponent, PurchaseDetailComponent, PurchaseUpdateComponent, PurchaseDeleteDialogComponent],
  entryComponents: [PurchaseDeleteDialogComponent],
})
export class JhipsterSampleApplication1PurchaseModule {}
