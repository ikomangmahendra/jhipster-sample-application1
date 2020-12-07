import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUnit } from 'app/shared/model/unit.model';

type EntityResponseType = HttpResponse<IUnit>;
type EntityArrayResponseType = HttpResponse<IUnit[]>;

@Injectable({ providedIn: 'root' })
export class UnitService {
  public resourceUrl = SERVER_API_URL + 'api/units';

  constructor(protected http: HttpClient) {}

  create(unit: IUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(unit);
    return this.http
      .post<IUnit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(unit: IUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(unit);
    return this.http
      .put<IUnit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUnit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(unit: IUnit): IUnit {
    const copy: IUnit = Object.assign({}, unit, {
      createdDate: unit.createdDate && unit.createdDate.isValid() ? unit.createdDate.toJSON() : undefined,
      lastModifiedDate: unit.lastModifiedDate && unit.lastModifiedDate.isValid() ? unit.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((unit: IUnit) => {
        unit.createdDate = unit.createdDate ? moment(unit.createdDate) : undefined;
        unit.lastModifiedDate = unit.lastModifiedDate ? moment(unit.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
