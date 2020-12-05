import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhipsterSampleApplication1SharedModule } from 'app/shared/shared.module';
import { JhipsterSampleApplication1CoreModule } from 'app/core/core.module';
import { JhipsterSampleApplication1AppRoutingModule } from './app-routing.module';
import { JhipsterSampleApplication1HomeModule } from './home/home.module';
import { JhipsterSampleApplication1EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JhipsterSampleApplication1SharedModule,
    JhipsterSampleApplication1CoreModule,
    JhipsterSampleApplication1HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhipsterSampleApplication1EntityModule,
    JhipsterSampleApplication1AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class JhipsterSampleApplication1AppModule {}
