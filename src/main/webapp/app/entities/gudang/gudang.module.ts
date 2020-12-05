import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication1SharedModule } from 'app/shared/shared.module';
import { GudangComponent } from './gudang.component';
import { GudangDetailComponent } from './gudang-detail.component';
import { GudangUpdateComponent } from './gudang-update.component';
import { GudangDeleteDialogComponent } from './gudang-delete-dialog.component';
import { gudangRoute } from './gudang.route';

@NgModule({
  imports: [JhipsterSampleApplication1SharedModule, RouterModule.forChild(gudangRoute)],
  declarations: [GudangComponent, GudangDetailComponent, GudangUpdateComponent, GudangDeleteDialogComponent],
  entryComponents: [GudangDeleteDialogComponent],
})
export class JhipsterSampleApplication1GudangModule {}
