import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBarang } from 'app/shared/model/barang.model';

@Component({
  selector: 'jhi-barang-detail',
  templateUrl: './barang-detail.component.html',
})
export class BarangDetailComponent implements OnInit {
  barang: IBarang | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ barang }) => (this.barang = barang));
  }

  previousState(): void {
    window.history.back();
  }
}
