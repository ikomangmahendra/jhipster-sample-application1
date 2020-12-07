import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGudang, Gudang } from 'app/shared/model/gudang.model';
import { GudangService } from './gudang.service';

@Component({
  selector: 'jhi-gudang-update',
  templateUrl: './gudang-update.component.html',
})
export class GudangUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(50)]],
  });

  constructor(protected gudangService: GudangService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gudang }) => {
      this.updateForm(gudang);
    });
  }

  updateForm(gudang: IGudang): void {
    this.editForm.patchValue({
      id: gudang.id,
      name: gudang.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gudang = this.createFromForm();
    if (gudang.id !== undefined) {
      this.subscribeToSaveResponse(this.gudangService.update(gudang));
    } else {
      this.subscribeToSaveResponse(this.gudangService.create(gudang));
    }
  }

  private createFromForm(): IGudang {
    return {
      ...new Gudang(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGudang>>): void {
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
