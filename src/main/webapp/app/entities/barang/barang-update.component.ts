import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBarang, Barang } from 'app/shared/model/barang.model';
import { BarangService } from './barang.service';

@Component({
  selector: 'jhi-barang-update',
  templateUrl: './barang-update.component.html',
})
export class BarangUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(50)]],
  });

  constructor(protected barangService: BarangService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ barang }) => {
      this.updateForm(barang);
    });
  }

  updateForm(barang: IBarang): void {
    this.editForm.patchValue({
      id: barang.id,
      name: barang.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const barang = this.createFromForm();
    if (barang.id !== undefined) {
      this.subscribeToSaveResponse(this.barangService.update(barang));
    } else {
      this.subscribeToSaveResponse(this.barangService.create(barang));
    }
  }

  private createFromForm(): IBarang {
    return {
      ...new Barang(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBarang>>): void {
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
