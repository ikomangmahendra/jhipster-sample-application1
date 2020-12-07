import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPurchaseItem, PurchaseItem } from 'app/shared/model/purchase-item.model';
import { PurchaseItemService } from './purchase-item.service';
import { ICatalogItem } from 'app/shared/model/catalog-item.model';
import { CatalogItemService } from 'app/entities/catalog-item/catalog-item.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { ITax } from 'app/shared/model/tax.model';
import { TaxService } from 'app/entities/tax/tax.service';
import { IPurchase } from 'app/shared/model/purchase.model';
import { PurchaseService } from 'app/entities/purchase/purchase.service';

type SelectableEntity = ICatalogItem | IUnit | ITax | IPurchase;

@Component({
  selector: 'jhi-purchase-item-update',
  templateUrl: './purchase-item-update.component.html',
})
export class PurchaseItemUpdateComponent implements OnInit {
  isSaving = false;
  catalogitems: ICatalogItem[] = [];
  units: IUnit[] = [];
  taxes: ITax[] = [];
  purchases: IPurchase[] = [];

  editForm = this.fb.group({
    id: [],
    typeId: [null, [Validators.required]],
    qty: [null, [Validators.required]],
    price: [null, [Validators.required]],
    disc: [],
    discPercentStatus: [],
    taxAmount: [null, [Validators.required]],
    note: [null, [Validators.maxLength(20)]],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.maxLength(50)]],
    lastModifiedDate: [],
    recordStatusId: [null, [Validators.required]],
    catalogId: [],
    unitId: [],
    taxId: [],
    purchaseId: [],
  });

  constructor(
    protected purchaseItemService: PurchaseItemService,
    protected catalogItemService: CatalogItemService,
    protected unitService: UnitService,
    protected taxService: TaxService,
    protected purchaseService: PurchaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseItem }) => {
      if (!purchaseItem.id) {
        const today = moment().startOf('day');
        purchaseItem.createdDate = today;
        purchaseItem.lastModifiedDate = today;
      }

      this.updateForm(purchaseItem);

      this.catalogItemService.query().subscribe((res: HttpResponse<ICatalogItem[]>) => (this.catalogitems = res.body || []));

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));

      this.taxService.query().subscribe((res: HttpResponse<ITax[]>) => (this.taxes = res.body || []));

      this.purchaseService.query().subscribe((res: HttpResponse<IPurchase[]>) => (this.purchases = res.body || []));
    });
  }

  updateForm(purchaseItem: IPurchaseItem): void {
    this.editForm.patchValue({
      id: purchaseItem.id,
      typeId: purchaseItem.typeId,
      qty: purchaseItem.qty,
      price: purchaseItem.price,
      disc: purchaseItem.disc,
      discPercentStatus: purchaseItem.discPercentStatus,
      taxAmount: purchaseItem.taxAmount,
      note: purchaseItem.note,
      createdBy: purchaseItem.createdBy,
      createdDate: purchaseItem.createdDate ? purchaseItem.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: purchaseItem.lastModifiedBy,
      lastModifiedDate: purchaseItem.lastModifiedDate ? purchaseItem.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      recordStatusId: purchaseItem.recordStatusId,
      catalogId: purchaseItem.catalogId,
      unitId: purchaseItem.unitId,
      taxId: purchaseItem.taxId,
      purchaseId: purchaseItem.purchaseId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purchaseItem = this.createFromForm();
    if (purchaseItem.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseItemService.update(purchaseItem));
    } else {
      this.subscribeToSaveResponse(this.purchaseItemService.create(purchaseItem));
    }
  }

  private createFromForm(): IPurchaseItem {
    return {
      ...new PurchaseItem(),
      id: this.editForm.get(['id'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      qty: this.editForm.get(['qty'])!.value,
      price: this.editForm.get(['price'])!.value,
      disc: this.editForm.get(['disc'])!.value,
      discPercentStatus: this.editForm.get(['discPercentStatus'])!.value,
      taxAmount: this.editForm.get(['taxAmount'])!.value,
      note: this.editForm.get(['note'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      recordStatusId: this.editForm.get(['recordStatusId'])!.value,
      catalogId: this.editForm.get(['catalogId'])!.value,
      unitId: this.editForm.get(['unitId'])!.value,
      taxId: this.editForm.get(['taxId'])!.value,
      purchaseId: this.editForm.get(['purchaseId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseItem>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
