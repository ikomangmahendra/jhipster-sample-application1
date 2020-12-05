import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISalesItem, SalesItem } from 'app/shared/model/sales-item.model';
import { SalesItemService } from './sales-item.service';
import { IBarang } from 'app/shared/model/barang.model';
import { BarangService } from 'app/entities/barang/barang.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { ITax } from 'app/shared/model/tax.model';
import { TaxService } from 'app/entities/tax/tax.service';
import { ISales } from 'app/shared/model/sales.model';
import { SalesService } from 'app/entities/sales/sales.service';

type SelectableEntity = IBarang | IUnit | ITax | ISales;

@Component({
  selector: 'jhi-sales-item-update',
  templateUrl: './sales-item-update.component.html',
})
export class SalesItemUpdateComponent implements OnInit {
  isSaving = false;
  barangs: IBarang[] = [];
  units: IUnit[] = [];
  taxes: ITax[] = [];
  sales: ISales[] = [];

  editForm = this.fb.group({
    id: [],
    typeId: [null, [Validators.required]],
    qty: [null, [Validators.required]],
    price: [null, [Validators.required]],
    disc: [],
    isDiscPercent: [],
    tax: [null, [Validators.required]],
    note: [null, [Validators.maxLength(20)]],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.maxLength(50)]],
    lastModifiedDate: [],
    recordStatusId: [null, [Validators.required]],
    catalogId: [],
    unitId: [],
    taxId: [],
    salesId: [],
  });

  constructor(
    protected salesItemService: SalesItemService,
    protected barangService: BarangService,
    protected unitService: UnitService,
    protected taxService: TaxService,
    protected salesService: SalesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesItem }) => {
      if (!salesItem.id) {
        const today = moment().startOf('day');
        salesItem.createdDate = today;
        salesItem.lastModifiedDate = today;
      }

      this.updateForm(salesItem);

      this.barangService.query().subscribe((res: HttpResponse<IBarang[]>) => (this.barangs = res.body || []));

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));

      this.taxService.query().subscribe((res: HttpResponse<ITax[]>) => (this.taxes = res.body || []));

      this.salesService.query().subscribe((res: HttpResponse<ISales[]>) => (this.sales = res.body || []));
    });
  }

  updateForm(salesItem: ISalesItem): void {
    this.editForm.patchValue({
      id: salesItem.id,
      typeId: salesItem.typeId,
      qty: salesItem.qty,
      price: salesItem.price,
      disc: salesItem.disc,
      isDiscPercent: salesItem.isDiscPercent,
      tax: salesItem.tax,
      note: salesItem.note,
      createdBy: salesItem.createdBy,
      createdDate: salesItem.createdDate ? salesItem.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: salesItem.lastModifiedBy,
      lastModifiedDate: salesItem.lastModifiedDate ? salesItem.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      recordStatusId: salesItem.recordStatusId,
      catalogId: salesItem.catalogId,
      unitId: salesItem.unitId,
      taxId: salesItem.taxId,
      salesId: salesItem.salesId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesItem = this.createFromForm();
    if (salesItem.id !== undefined) {
      this.subscribeToSaveResponse(this.salesItemService.update(salesItem));
    } else {
      this.subscribeToSaveResponse(this.salesItemService.create(salesItem));
    }
  }

  private createFromForm(): ISalesItem {
    return {
      ...new SalesItem(),
      id: this.editForm.get(['id'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      qty: this.editForm.get(['qty'])!.value,
      price: this.editForm.get(['price'])!.value,
      disc: this.editForm.get(['disc'])!.value,
      isDiscPercent: this.editForm.get(['isDiscPercent'])!.value,
      tax: this.editForm.get(['tax'])!.value,
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
      salesId: this.editForm.get(['salesId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesItem>>): void {
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
