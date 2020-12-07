import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGudang } from 'app/shared/model/gudang.model';

type EntityResponseType = HttpResponse<IGudang>;
type EntityArrayResponseType = HttpResponse<IGudang[]>;

@Injectable({ providedIn: 'root' })
export class GudangService {
  public resourceUrl = SERVER_API_URL + 'api/gudangs';

  constructor(protected http: HttpClient) {}

  create(gudang: IGudang): Observable<EntityResponseType> {
    return this.http.post<IGudang>(this.resourceUrl, gudang, { observe: 'response' });
  }

  update(gudang: IGudang): Observable<EntityResponseType> {
    return this.http.put<IGudang>(this.resourceUrl, gudang, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGudang>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGudang[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
