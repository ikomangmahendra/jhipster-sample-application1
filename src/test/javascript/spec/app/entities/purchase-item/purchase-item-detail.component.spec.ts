import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { PurchaseItemDetailComponent } from 'app/entities/purchase-item/purchase-item-detail.component';
import { PurchaseItem } from 'app/shared/model/purchase-item.model';

describe('Component Tests', () => {
  describe('PurchaseItem Management Detail Component', () => {
    let comp: PurchaseItemDetailComponent;
    let fixture: ComponentFixture<PurchaseItemDetailComponent>;
    const route = ({ data: of({ purchaseItem: new PurchaseItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [PurchaseItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PurchaseItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PurchaseItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load purchaseItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.purchaseItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
