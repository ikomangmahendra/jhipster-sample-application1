import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITax, Tax } from 'app/shared/model/tax.model';
import { TaxService } from './tax.service';

@Component({
  selector: 'jhi-tax-update',
  templateUrl: './tax-update.component.html',
})
export class TaxUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    name: [null, [Validators.required, Validators.maxLength(30)]],
    taxRage: [],
    description: [null, [Validators.maxLength(50)]],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.maxLength(50)]],
    lastModifiedDate: [],
    recordStatusId: [null, [Validators.required]],
  });

  constructor(protected taxService: TaxService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tax }) => {
      if (!tax.id) {
        const today = moment().startOf('day');
        tax.createdDate = today;
        tax.lastModifiedDate = today;
      }

      this.updateForm(tax);
    });
  }

  updateForm(tax: ITax): void {
    this.editForm.patchValue({
      id: tax.id,
      code: tax.code,
      name: tax.name,
      taxRage: tax.taxRage,
      description: tax.description,
      createdBy: tax.createdBy,
      createdDate: tax.createdDate ? tax.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: tax.lastModifiedBy,
      lastModifiedDate: tax.lastModifiedDate ? tax.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      recordStatusId: tax.recordStatusId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tax = this.createFromForm();
    if (tax.id !== undefined) {
      this.subscribeToSaveResponse(this.taxService.update(tax));
    } else {
      this.subscribeToSaveResponse(this.taxService.create(tax));
    }
  }

  private createFromForm(): ITax {
    return {
      ...new Tax(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      taxRage: this.editForm.get(['taxRage'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      recordStatusId: this.editForm.get(['recordStatusId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITax>>): void {
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
