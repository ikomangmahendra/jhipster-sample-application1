import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPurchaseItem, PurchaseItem } from 'app/shared/model/purchase-item.model';
import { PurchaseItemService } from './purchase-item.service';
import { PurchaseItemComponent } from './purchase-item.component';
import { PurchaseItemDetailComponent } from './purchase-item-detail.component';
import { PurchaseItemUpdateComponent } from './purchase-item-update.component';

@Injectable({ providedIn: 'root' })
export class PurchaseItemResolve implements Resolve<IPurchaseItem> {
  constructor(private service: PurchaseItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPurchaseItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((purchaseItem: HttpResponse<PurchaseItem>) => {
          if (purchaseItem.body) {
            return of(purchaseItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PurchaseItem());
  }
}

export const purchaseItemRoute: Routes = [
  {
    path: '',
    component: PurchaseItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PurchaseItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PurchaseItemDetailComponent,
    resolve: {
      purchaseItem: PurchaseItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PurchaseItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PurchaseItemUpdateComponent,
    resolve: {
      purchaseItem: PurchaseItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PurchaseItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PurchaseItemUpdateComponent,
    resolve: {
      purchaseItem: PurchaseItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PurchaseItems',
    },
    canActivate: [UserRouteAccessService],
  },
];
