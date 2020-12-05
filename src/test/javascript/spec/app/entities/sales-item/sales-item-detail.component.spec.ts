import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { SalesItemDetailComponent } from 'app/entities/sales-item/sales-item-detail.component';
import { SalesItem } from 'app/shared/model/sales-item.model';

describe('Component Tests', () => {
  describe('SalesItem Management Detail Component', () => {
    let comp: SalesItemDetailComponent;
    let fixture: ComponentFixture<SalesItemDetailComponent>;
    const route = ({ data: of({ salesItem: new SalesItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [SalesItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SalesItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SalesItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load salesItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.salesItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
