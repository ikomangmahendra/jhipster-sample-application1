import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISalesItem, SalesItem } from 'app/shared/model/sales-item.model';
import { SalesItemService } from './sales-item.service';
import { SalesItemComponent } from './sales-item.component';
import { SalesItemDetailComponent } from './sales-item-detail.component';
import { SalesItemUpdateComponent } from './sales-item-update.component';

@Injectable({ providedIn: 'root' })
export class SalesItemResolve implements Resolve<ISalesItem> {
  constructor(private service: SalesItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalesItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((salesItem: HttpResponse<SalesItem>) => {
          if (salesItem.body) {
            return of(salesItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SalesItem());
  }
}

export const salesItemRoute: Routes = [
  {
    path: '',
    component: SalesItemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'SalesItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SalesItemDetailComponent,
    resolve: {
      salesItem: SalesItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SalesItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SalesItemUpdateComponent,
    resolve: {
      salesItem: SalesItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SalesItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SalesItemUpdateComponent,
    resolve: {
      salesItem: SalesItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SalesItems',
    },
    canActivate: [UserRouteAccessService],
  },
];
