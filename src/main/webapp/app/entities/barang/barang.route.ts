import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBarang, Barang } from 'app/shared/model/barang.model';
import { BarangService } from './barang.service';
import { BarangComponent } from './barang.component';
import { BarangDetailComponent } from './barang-detail.component';
import { BarangUpdateComponent } from './barang-update.component';

@Injectable({ providedIn: 'root' })
export class BarangResolve implements Resolve<IBarang> {
  constructor(private service: BarangService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBarang> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((barang: HttpResponse<Barang>) => {
          if (barang.body) {
            return of(barang.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Barang());
  }
}

export const barangRoute: Routes = [
  {
    path: '',
    component: BarangComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Barangs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BarangDetailComponent,
    resolve: {
      barang: BarangResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Barangs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BarangUpdateComponent,
    resolve: {
      barang: BarangResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Barangs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BarangUpdateComponent,
    resolve: {
      barang: BarangResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Barangs',
    },
    canActivate: [UserRouteAccessService],
  },
];
