import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication1SharedModule } from 'app/shared/shared.module';
import { SalesItemComponent } from './sales-item.component';
import { SalesItemDetailComponent } from './sales-item-detail.component';
import { SalesItemUpdateComponent } from './sales-item-update.component';
import { SalesItemDeleteDialogComponent } from './sales-item-delete-dialog.component';
import { salesItemRoute } from './sales-item.route';

@NgModule({
  imports: [JhipsterSampleApplication1SharedModule, RouterModule.forChild(salesItemRoute)],
  declarations: [SalesItemComponent, SalesItemDetailComponent, SalesItemUpdateComponent, SalesItemDeleteDialogComponent],
  entryComponents: [SalesItemDeleteDialogComponent],
})
export class JhipsterSampleApplication1SalesItemModule {}
