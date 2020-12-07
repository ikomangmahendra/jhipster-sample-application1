import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatalogItem, CatalogItem } from 'app/shared/model/catalog-item.model';
import { CatalogItemService } from './catalog-item.service';

@Component({
  selector: 'jhi-catalog-item-update',
  templateUrl: './catalog-item-update.component.html',
})
export class CatalogItemUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(50)]],
  });

  constructor(protected catalogItemService: CatalogItemService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogItem }) => {
      this.updateForm(catalogItem);
    });
  }

  updateForm(catalogItem: ICatalogItem): void {
    this.editForm.patchValue({
      id: catalogItem.id,
      name: catalogItem.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catalogItem = this.createFromForm();
    if (catalogItem.id !== undefined) {
      this.subscribeToSaveResponse(this.catalogItemService.update(catalogItem));
    } else {
      this.subscribeToSaveResponse(this.catalogItemService.create(catalogItem));
    }
  }

  private createFromForm(): ICatalogItem {
    return {
      ...new CatalogItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatalogItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
