import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatalogItem } from 'app/shared/model/catalog-item.model';

@Component({
  selector: 'jhi-catalog-item-detail',
  templateUrl: './catalog-item-detail.component.html',
})
export class CatalogItemDetailComponent implements OnInit {
  catalogItem: ICatalogItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogItem }) => (this.catalogItem = catalogItem));
  }

  previousState(): void {
    window.history.back();
  }
}
