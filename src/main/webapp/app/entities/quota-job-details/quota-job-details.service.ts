import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { QuotaJobDetails } from './quota-job-details.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class QuotaJobDetailsService {

    private resourceUrl = 'api/quota-job-details';
    private resourceSearchUrl = 'api/_search/quota-job-details';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(quotaJobDetails: QuotaJobDetails): Observable<QuotaJobDetails> {
        const copy = this.convert(quotaJobDetails);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(quotaJobDetails: QuotaJobDetails): Observable<QuotaJobDetails> {
        const copy = this.convert(quotaJobDetails);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<QuotaJobDetails> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.bornBefore = this.dateUtils
            .convertLocalDateFromServer(entity.bornBefore);
        entity.bornAfter = this.dateUtils
            .convertLocalDateFromServer(entity.bornAfter);
    }

    private convert(quotaJobDetails: QuotaJobDetails): QuotaJobDetails {
        const copy: QuotaJobDetails = Object.assign({}, quotaJobDetails);
        copy.bornBefore = this.dateUtils
            .convertLocalDateToServer(quotaJobDetails.bornBefore);
        copy.bornAfter = this.dateUtils
            .convertLocalDateToServer(quotaJobDetails.bornAfter);
        return copy;
    }
}
