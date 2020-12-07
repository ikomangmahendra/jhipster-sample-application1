import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPurchaseItem } from 'app/shared/model/purchase-item.model';

type EntityResponseType = HttpResponse<IPurchaseItem>;
type EntityArrayResponseType = HttpResponse<IPurchaseItem[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseItemService {
  public resourceUrl = SERVER_API_URL + 'api/purchase-items';

  constructor(protected http: HttpClient) {}

  create(purchaseItem: IPurchaseItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseItem);
    return this.http
      .post<IPurchaseItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(purchaseItem: IPurchaseItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseItem);
    return this.http
      .put<IPurchaseItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPurchaseItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPurchaseItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(purchaseItem: IPurchaseItem): IPurchaseItem {
    const copy: IPurchaseItem = Object.assign({}, purchaseItem, {
      createdDate: purchaseItem.createdDate && purchaseItem.createdDate.isValid() ? purchaseItem.createdDate.toJSON() : undefined,
      lastModifiedDate:
        purchaseItem.lastModifiedDate && purchaseItem.lastModifiedDate.isValid() ? purchaseItem.lastModifiedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((purchaseItem: IPurchaseItem) => {
        purchaseItem.createdDate = purchaseItem.createdDate ? moment(purchaseItem.createdDate) : undefined;
        purchaseItem.lastModifiedDate = purchaseItem.lastModifiedDate ? moment(purchaseItem.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
