import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISalesItem } from 'app/shared/model/sales-item.model';

type EntityResponseType = HttpResponse<ISalesItem>;
type EntityArrayResponseType = HttpResponse<ISalesItem[]>;

@Injectable({ providedIn: 'root' })
export class SalesItemService {
  public resourceUrl = SERVER_API_URL + 'api/sales-items';

  constructor(protected http: HttpClient) {}

  create(salesItem: ISalesItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesItem);
    return this.http
      .post<ISalesItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(salesItem: ISalesItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesItem);
    return this.http
      .put<ISalesItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISalesItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISalesItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(salesItem: ISalesItem): ISalesItem {
    const copy: ISalesItem = Object.assign({}, salesItem, {
      createdDate: salesItem.createdDate && salesItem.createdDate.isValid() ? salesItem.createdDate.toJSON() : undefined,
      lastModifiedDate:
        salesItem.lastModifiedDate && salesItem.lastModifiedDate.isValid() ? salesItem.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((salesItem: ISalesItem) => {
        salesItem.createdDate = salesItem.createdDate ? moment(salesItem.createdDate) : undefined;
        salesItem.lastModifiedDate = salesItem.lastModifiedDate ? moment(salesItem.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
