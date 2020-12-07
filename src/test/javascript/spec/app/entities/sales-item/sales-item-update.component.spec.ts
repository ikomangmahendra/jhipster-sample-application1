import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { SalesItemUpdateComponent } from 'app/entities/sales-item/sales-item-update.component';
import { SalesItemService } from 'app/entities/sales-item/sales-item.service';
import { SalesItem } from 'app/shared/model/sales-item.model';

describe('Component Tests', () => {
  describe('SalesItem Management Update Component', () => {
    let comp: SalesItemUpdateComponent;
    let fixture: ComponentFixture<SalesItemUpdateComponent>;
    let service: SalesItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [SalesItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SalesItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalesItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalesItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SalesItem(123);
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
        const entity = new SalesItem();
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
