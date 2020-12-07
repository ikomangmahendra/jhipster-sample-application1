import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGudang } from 'app/shared/model/gudang.model';

@Component({
  selector: 'jhi-gudang-detail',
  templateUrl: './gudang-detail.component.html',
})
export class GudangDetailComponent implements OnInit {
  gudang: IGudang | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gudang }) => (this.gudang = gudang));
  }

  previousState(): void {
    window.history.back();
  }
}
