import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { CatalogItemDetailComponent } from 'app/entities/catalog-item/catalog-item-detail.component';
import { CatalogItem } from 'app/shared/model/catalog-item.model';

describe('Component Tests', () => {
  describe('CatalogItem Management Detail Component', () => {
    let comp: CatalogItemDetailComponent;
    let fixture: ComponentFixture<CatalogItemDetailComponent>;
    const route = ({ data: of({ catalogItem: new CatalogItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [CatalogItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatalogItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatalogItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catalogItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catalogItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
