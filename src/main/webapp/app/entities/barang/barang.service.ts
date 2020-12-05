import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBarang } from 'app/shared/model/barang.model';

type EntityResponseType = HttpResponse<IBarang>;
type EntityArrayResponseType = HttpResponse<IBarang[]>;

@Injectable({ providedIn: 'root' })
export class BarangService {
  public resourceUrl = SERVER_API_URL + 'api/barangs';

  constructor(protected http: HttpClient) {}

  create(barang: IBarang): Observable<EntityResponseType> {
    return this.http.post<IBarang>(this.resourceUrl, barang, { observe: 'response' });
  }

  update(barang: IBarang): Observable<EntityResponseType> {
    return this.http.put<IBarang>(this.resourceUrl, barang, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBarang>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBarang[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
