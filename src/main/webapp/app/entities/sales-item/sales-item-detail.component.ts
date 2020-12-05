import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalesItem } from 'app/shared/model/sales-item.model';

@Component({
  selector: 'jhi-sales-item-detail',
  templateUrl: './sales-item-detail.component.html',
})
export class SalesItemDetailComponent implements OnInit {
  salesItem: ISalesItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesItem }) => (this.salesItem = salesItem));
  }

  previousState(): void {
    window.history.back();
  }
}
