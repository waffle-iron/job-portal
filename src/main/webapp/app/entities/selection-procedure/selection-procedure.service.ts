import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SelectionProcedure } from './selection-procedure.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SelectionProcedureService {

    private resourceUrl = 'api/selection-procedures';
    private resourceSearchUrl = 'api/_search/selection-procedures';

    constructor(private http: Http) { }

    create(selectionProcedure: SelectionProcedure): Observable<SelectionProcedure> {
        const copy = this.convert(selectionProcedure);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(selectionProcedure: SelectionProcedure): Observable<SelectionProcedure> {
        const copy = this.convert(selectionProcedure);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<SelectionProcedure> {
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

    private convert(selectionProcedure: SelectionProcedure): SelectionProcedure {
        const copy: SelectionProcedure = Object.assign({}, selectionProcedure);
        return copy;
    }
}
