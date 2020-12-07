import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplication1TestModule } from '../../../test.module';
import { CatalogItemUpdateComponent } from 'app/entities/catalog-item/catalog-item-update.component';
import { CatalogItemService } from 'app/entities/catalog-item/catalog-item.service';
import { CatalogItem } from 'app/shared/model/catalog-item.model';

describe('Component Tests', () => {
  describe('CatalogItem Management Update Component', () => {
    let comp: CatalogItemUpdateComponent;
    let fixture: ComponentFixture<CatalogItemUpdateComponent>;
    let service: CatalogItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplication1TestModule],
        declarations: [CatalogItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatalogItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatalogItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatalogItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatalogItem(123);
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
        const entity = new CatalogItem();
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
