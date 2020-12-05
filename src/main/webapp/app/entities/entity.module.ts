import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'unit',
        loadChildren: () => import('./unit/unit.module').then(m => m.JhipsterSampleApplication1UnitModule),
      },
      {
        path: 'tax',
        loadChildren: () => import('./tax/tax.module').then(m => m.JhipsterSampleApplication1TaxModule),
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.JhipsterSampleApplication1ContactModule),
      },
      {
        path: 'gudang',
        loadChildren: () => import('./gudang/gudang.module').then(m => m.JhipsterSampleApplication1GudangModule),
      },
      {
        path: 'barang',
        loadChildren: () => import('./barang/barang.module').then(m => m.JhipsterSampleApplication1BarangModule),
      },
      {
        path: 'purchase',
        loadChildren: () => import('./purchase/purchase.module').then(m => m.JhipsterSampleApplication1PurchaseModule),
      },
      {
        path: 'purchase-item',
        loadChildren: () => import('./purchase-item/purchase-item.module').then(m => m.JhipsterSampleApplication1PurchaseItemModule),
      },
      {
        path: 'sales',
        loadChildren: () => import('./sales/sales.module').then(m => m.JhipsterSampleApplication1SalesModule),
      },
      {
        path: 'sales-item',
        loadChildren: () => import('./sales-item/sales-item.module').then(m => m.JhipsterSampleApplication1SalesItemModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplication1EntityModule {}
