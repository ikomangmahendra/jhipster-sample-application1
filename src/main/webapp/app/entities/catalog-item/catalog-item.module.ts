import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication1SharedModule } from 'app/shared/shared.module';
import { CatalogItemComponent } from './catalog-item.component';
import { CatalogItemDetailComponent } from './catalog-item-detail.component';
import { CatalogItemUpdateComponent } from './catalog-item-update.component';
import { CatalogItemDeleteDialogComponent } from './catalog-item-delete-dialog.component';
import { catalogItemRoute } from './catalog-item.route';

@NgModule({
  imports: [JhipsterSampleApplication1SharedModule, RouterModule.forChild(catalogItemRoute)],
  declarations: [CatalogItemComponent, CatalogItemDetailComponent, CatalogItemUpdateComponent, CatalogItemDeleteDialogComponent],
  entryComponents: [CatalogItemDeleteDialogComponent],
})
export class JhipsterSampleApplication1CatalogItemModule {}
