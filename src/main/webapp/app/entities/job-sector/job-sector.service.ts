import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { JobSector } from './job-sector.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class JobSectorService {

    private resourceUrl = 'api/job-sectors';
    private resourceSearchUrl = 'api/_search/job-sectors';

    constructor(private http: Http) { }

    create(jobSector: JobSector): Observable<JobSector> {
        const copy = this.convert(jobSector);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(jobSector: JobSector): Observable<JobSector> {
        const copy = this.convert(jobSector);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<JobSector> {
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

    private convert(jobSector: JobSector): JobSector {
        const copy: JobSector = Object.assign({}, jobSector);
        return copy;
    }
}
