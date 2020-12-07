import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { BarangUpdateComponent } from 'app/entities/barang/barang-update.component';
import { BarangService } from 'app/entities/barang/barang.service';
import { Barang } from 'app/shared/model/barang.model';

describe('Component Tests', () => {
  describe('Barang Management Update Component', () => {
    let comp: BarangUpdateComponent;
    let fixture: ComponentFixture<BarangUpdateComponent>;
    let service: BarangService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [BarangUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BarangUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BarangUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BarangService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Barang(123);
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
        const entity = new Barang();
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
