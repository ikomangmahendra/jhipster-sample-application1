import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication1SharedModule } from 'app/shared/shared.module';
import { BarangComponent } from './barang.component';
import { BarangDetailComponent } from './barang-detail.component';
import { BarangUpdateComponent } from './barang-update.component';
import { BarangDeleteDialogComponent } from './barang-delete-dialog.component';
import { barangRoute } from './barang.route';

@NgModule({
  imports: [JhipsterSampleApplication1SharedModule, RouterModule.forChild(barangRoute)],
  declarations: [BarangComponent, BarangDetailComponent, BarangUpdateComponent, BarangDeleteDialogComponent],
  entryComponents: [BarangDeleteDialogComponent],
})
export class JhipsterSampleApplication1BarangModule {}
