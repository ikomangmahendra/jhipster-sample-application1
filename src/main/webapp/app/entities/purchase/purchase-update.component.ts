import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPurchase, Purchase } from 'app/shared/model/purchase.model';
import { PurchaseService } from './purchase.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';
import { IGudang } from 'app/shared/model/gudang.model';
import { GudangService } from 'app/entities/gudang/gudang.service';

type SelectableEntity = IContact | IGudang;

@Component({
  selector: 'jhi-purchase-update',
  templateUrl: './purchase-update.component.html',
})
export class PurchaseUpdateComponent implements OnInit {
  isSaving = false;
  contacts: IContact[] = [];
  gudangs: IGudang[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    typeId: [null, [Validators.required]],
    date: [null, [Validators.required]],
    refNo: [null, [Validators.required, Validators.maxLength(20)]],
    description: [null, [Validators.maxLength(100)]],
    otherCost: [],
    subtotal: [null, [Validators.required]],
    totalTax: [null, [Validators.required]],
    disc: [],
    isDiscPercent: [],
    status: [null, [Validators.maxLength(2)]],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.maxLength(50)]],
    lastModifiedDate: [],
    recordStatusId: [null, [Validators.required]],
    contactId: [],
    warehouseId: [],
  });

  constructor(
    protected purchaseService: PurchaseService,
    protected contactService: ContactService,
    protected gudangService: GudangService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchase }) => {
      if (!purchase.id) {
        const today = moment().startOf('day');
        purchase.createdDate = today;
        purchase.lastModifiedDate = today;
      }

      this.updateForm(purchase);

      this.contactService.query().subscribe((res: HttpResponse<IContact[]>) => (this.contacts = res.body || []));

      this.gudangService.query().subscribe((res: HttpResponse<IGudang[]>) => (this.gudangs = res.body || []));
    });
  }

  updateForm(purchase: IPurchase): void {
    this.editForm.patchValue({
      id: purchase.id,
      typeId: purchase.typeId,
      date: purchase.date,
      refNo: purchase.refNo,
      description: purchase.description,
      otherCost: purchase.otherCost,
      subtotal: purchase.subtotal,
      totalTax: purchase.totalTax,
      disc: purchase.disc,
      isDiscPercent: purchase.isDiscPercent,
      status: purchase.status,
      createdBy: purchase.createdBy,
      createdDate: purchase.createdDate ? purchase.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: purchase.lastModifiedBy,
      lastModifiedDate: purchase.lastModifiedDate ? purchase.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      recordStatusId: purchase.recordStatusId,
      contactId: purchase.contactId,
      warehouseId: purchase.warehouseId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purchase = this.createFromForm();
    if (purchase.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseService.update(purchase));
    } else {
      this.subscribeToSaveResponse(this.purchaseService.create(purchase));
    }
  }

  private createFromForm(): IPurchase {
    return {
      ...new Purchase(),
      id: this.editForm.get(['id'])!.value,
      typeId: this.editForm.get(['typeId'])!.value,
      date: this.editForm.get(['date'])!.value,
      refNo: this.editForm.get(['refNo'])!.value,
      description: this.editForm.get(['description'])!.value,
      otherCost: this.editForm.get(['otherCost'])!.value,
      subtotal: this.editForm.get(['subtotal'])!.value,
      totalTax: this.editForm.get(['totalTax'])!.value,
      disc: this.editForm.get(['disc'])!.value,
      isDiscPercent: this.editForm.get(['isDiscPercent'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      recordStatusId: this.editForm.get(['recordStatusId'])!.value,
      contactId: this.editForm.get(['contactId'])!.value,
      warehouseId: this.editForm.get(['warehouseId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchase>>): void {
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
