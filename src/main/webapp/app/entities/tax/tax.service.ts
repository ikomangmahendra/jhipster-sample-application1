import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITax } from 'app/shared/model/tax.model';

type EntityResponseType = HttpResponse<ITax>;
type EntityArrayResponseType = HttpResponse<ITax[]>;

@Injectable({ providedIn: 'root' })
export class TaxService {
  public resourceUrl = SERVER_API_URL + 'api/taxes';

  constructor(protected http: HttpClient) {}

  create(tax: ITax): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tax);
    return this.http
      .post<ITax>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tax: ITax): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tax);
    return this.http
      .put<ITax>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITax>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITax[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tax: ITax): ITax {
    const copy: ITax = Object.assign({}, tax, {
      createdDate: tax.createdDate && tax.createdDate.isValid() ? tax.createdDate.toJSON() : undefined,
      lastModifiedDate: tax.lastModifiedDate && tax.lastModifiedDate.isValid() ? tax.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((tax: ITax) => {
        tax.createdDate = tax.createdDate ? moment(tax.createdDate) : undefined;
        tax.lastModifiedDate = tax.lastModifiedDate ? moment(tax.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
