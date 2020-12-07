import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplication1SharedModule } from 'app/shared/shared.module';
import { ContactComponent } from './contact.component';
import { ContactDetailComponent } from './contact-detail.component';
import { ContactUpdateComponent } from './contact-update.component';
import { ContactDeleteDialogComponent } from './contact-delete-dialog.component';
import { contactRoute } from './contact.route';

@NgModule({
  imports: [JhipsterSampleApplication1SharedModule, RouterModule.forChild(contactRoute)],
  declarations: [ContactComponent, ContactDetailComponent, ContactUpdateComponent, ContactDeleteDialogComponent],
  entryComponents: [ContactDeleteDialogComponent],
})
export class JhipsterSampleApplication1ContactModule {}
