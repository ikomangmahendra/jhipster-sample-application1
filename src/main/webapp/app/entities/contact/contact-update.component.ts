import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContact, Contact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';

@Component({
  selector: 'jhi-contact-update',
  templateUrl: './contact-update.component.html',
})
export class ContactUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    customerStatus: [],
    supplierStatus: [],
    code: [null, [Validators.required, Validators.maxLength(10)]],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    phone1: [null, [Validators.maxLength(20)]],
    phone2: [null, [Validators.maxLength(20)]],
    email: [null, [Validators.maxLength(100)]],
    address: [null, [Validators.maxLength(100)]],
    city: [null, [Validators.maxLength(30)]],
    province: [null, [Validators.maxLength(50)]],
    postCode: [null, [Validators.maxLength(10)]],
    salesName: [null, [Validators.maxLength(50)]],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.maxLength(50)]],
    lastModifiedDate: [],
    recordStatusId: [null, [Validators.required]],
  });

  constructor(protected contactService: ContactService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      if (!contact.id) {
        const today = moment().startOf('day');
        contact.createdDate = today;
        contact.lastModifiedDate = today;
      }

      this.updateForm(contact);
    });
  }

  updateForm(contact: IContact): void {
    this.editForm.patchValue({
      id: contact.id,
      customerStatus: contact.customerStatus,
      supplierStatus: contact.supplierStatus,
      code: contact.code,
      name: contact.name,
      phone1: contact.phone1,
      phone2: contact.phone2,
      email: contact.email,
      address: contact.address,
      city: contact.city,
      province: contact.province,
      postCode: contact.postCode,
      salesName: contact.salesName,
      createdBy: contact.createdBy,
      createdDate: contact.createdDate ? contact.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: contact.lastModifiedBy,
      lastModifiedDate: contact.lastModifiedDate ? contact.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      recordStatusId: contact.recordStatusId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contact = this.createFromForm();
    if (contact.id !== undefined) {
      this.subscribeToSaveResponse(this.contactService.update(contact));
    } else {
      this.subscribeToSaveResponse(this.contactService.create(contact));
    }
  }

  private createFromForm(): IContact {
    return {
      ...new Contact(),
      id: this.editForm.get(['id'])!.value,
      customerStatus: this.editForm.get(['customerStatus'])!.value,
      supplierStatus: this.editForm.get(['supplierStatus'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      phone1: this.editForm.get(['phone1'])!.value,
      phone2: this.editForm.get(['phone2'])!.value,
      email: this.editForm.get(['email'])!.value,
      address: this.editForm.get(['address'])!.value,
      city: this.editForm.get(['city'])!.value,
      province: this.editForm.get(['province'])!.value,
      postCode: this.editForm.get(['postCode'])!.value,
      salesName: this.editForm.get(['salesName'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>): void {
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
