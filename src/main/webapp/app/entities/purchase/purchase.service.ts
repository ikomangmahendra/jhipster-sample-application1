import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPurchase } from 'app/shared/model/purchase.model';

type EntityResponseType = HttpResponse<IPurchase>;
type EntityArrayResponseType = HttpResponse<IPurchase[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseService {
  public resourceUrl = SERVER_API_URL + 'api/purchases';

  constructor(protected http: HttpClient) {}

  create(purchase: IPurchase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchase);
    return this.http
      .post<IPurchase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(purchase: IPurchase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchase);
    return this.http
      .put<IPurchase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPurchase>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPurchase[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(purchase: IPurchase): IPurchase {
    const copy: IPurchase = Object.assign({}, purchase, {
      date: purchase.date && purchase.date.isValid() ? purchase.date.format(DATE_FORMAT) : undefined,
      createdDate: purchase.createdDate && purchase.createdDate.isValid() ? purchase.createdDate.toJSON() : undefined,
      lastModifiedDate: purchase.lastModifiedDate && purchase.lastModifiedDate.isValid() ? purchase.lastModifiedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((purchase: IPurchase) => {
        purchase.date = purchase.date ? moment(purchase.date) : undefined;
        purchase.createdDate = purchase.createdDate ? moment(purchase.createdDate) : undefined;
        purchase.lastModifiedDate = purchase.lastModifiedDate ? moment(purchase.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
