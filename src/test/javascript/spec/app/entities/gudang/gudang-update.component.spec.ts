import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { GudangUpdateComponent } from 'app/entities/gudang/gudang-update.component';
import { GudangService } from 'app/entities/gudang/gudang.service';
import { Gudang } from 'app/shared/model/gudang.model';

describe('Component Tests', () => {
  describe('Gudang Management Update Component', () => {
    let comp: GudangUpdateComponent;
    let fixture: ComponentFixture<GudangUpdateComponent>;
    let service: GudangService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [GudangUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GudangUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GudangUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GudangService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Gudang(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Gudang();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
