import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { JobNotification } from './job-notification.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class JobNotificationService {

    private resourceUrl = 'api/job-notifications';
    private resourceSearchUrl = 'api/_search/job-notifications';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(jobNotification: JobNotification): Observable<JobNotification> {
        const copy = this.convert(jobNotification);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(jobNotification: JobNotification): Observable<JobNotification> {
        const copy = this.convert(jobNotification);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<JobNotification> {
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
        entity.notificationDate = this.dateUtils
            .convertLocalDateFromServer(entity.notificationDate);
        entity.applicationDeadline = this.dateUtils
            .convertLocalDateFromServer(entity.applicationDeadline);
    }

    private convert(jobNotification: JobNotification): JobNotification {
        const copy: JobNotification = Object.assign({}, jobNotification);
        copy.notificationDate = this.dateUtils
            .convertLocalDateToServer(jobNotification.notificationDate);
        copy.applicationDeadline = this.dateUtils
            .convertLocalDateToServer(jobNotification.applicationDeadline);
        return copy;
    }
}
