import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ClientType } from './client-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClientTypeService {

    private resourceUrl = 'api/client-types';
    private resourceSearchUrl = 'api/_search/client-types';

    constructor(private http: Http) { }

    create(clientType: ClientType): Observable<ClientType> {
        const copy = this.convert(clientType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(clientType: ClientType): Observable<ClientType> {
        const copy = this.convert(clientType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ClientType> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(clientType: ClientType): ClientType {
        const copy: ClientType = Object.assign({}, clientType);
        return copy;
    }
}
