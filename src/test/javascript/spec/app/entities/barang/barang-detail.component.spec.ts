import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { BarangDetailComponent } from 'app/entities/barang/barang-detail.component';
import { Barang } from 'app/shared/model/barang.model';

describe('Component Tests', () => {
  describe('Barang Management Detail Component', () => {
    let comp: BarangDetailComponent;
    let fixture: ComponentFixture<BarangDetailComponent>;
    const route = ({ data: of({ barang: new Barang(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [BarangDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BarangDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BarangDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load barang on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.barang).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
