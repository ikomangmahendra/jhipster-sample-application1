import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGudang, Gudang } from 'app/shared/model/gudang.model';
import { GudangService } from './gudang.service';
import { GudangComponent } from './gudang.component';
import { GudangDetailComponent } from './gudang-detail.component';
import { GudangUpdateComponent } from './gudang-update.component';

@Injectable({ providedIn: 'root' })
export class GudangResolve implements Resolve<IGudang> {
  constructor(private service: GudangService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGudang> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gudang: HttpResponse<Gudang>) => {
          if (gudang.body) {
            return of(gudang.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Gudang());
  }
}

export const gudangRoute: Routes = [
  {
    path: '',
    component: GudangComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Gudangs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GudangDetailComponent,
    resolve: {
      gudang: GudangResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Gudangs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GudangUpdateComponent,
    resolve: {
      gudang: GudangResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Gudangs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GudangUpdateComponent,
    resolve: {
      gudang: GudangResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Gudangs',
    },
    canActivate: [UserRouteAccessService],
  },
];
