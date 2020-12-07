import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { GudangDetailComponent } from 'app/entities/gudang/gudang-detail.component';
import { Gudang } from 'app/shared/model/gudang.model';

describe('Component Tests', () => {
  describe('Gudang Management Detail Component', () => {
    let comp: GudangDetailComponent;
    let fixture: ComponentFixture<GudangDetailComponent>;
    const route = ({ data: of({ gudang: new Gudang(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [GudangDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GudangDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GudangDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gudang on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gudang).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
