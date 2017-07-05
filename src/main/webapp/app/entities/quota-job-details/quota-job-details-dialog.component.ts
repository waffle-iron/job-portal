import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { QuotaJobDetails } from './quota-job-details.model';
import { QuotaJobDetailsPopupService } from './quota-job-details-popup.service';
import { QuotaJobDetailsService } from './quota-job-details.service';
import { QuotaCategory, QuotaCategoryService } from '../quota-category';
import { JobNotification, JobNotificationService } from '../job-notification';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-quota-job-details-dialog',
    templateUrl: './quota-job-details-dialog.component.html'
})
export class QuotaJobDetailsDialogComponent implements OnInit {

    quotaJobDetails: QuotaJobDetails;
    authorities: any[];
    isSaving: boolean;

    quotacategories: QuotaCategory[];

    jobnotifications: JobNotification[];
    bornBeforeDp: any;
    bornAfterDp: any;

    today = new Date();
    maxDate = {year: this.today.getFullYear(), month: this.today.getMonth()+1, day: this.today.getDate()};
    minDate = {year: this.today.getFullYear()-100, month: this.today.getMonth()+1, day: this.today.getDate()};

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private quotaJobDetailsService: QuotaJobDetailsService,
        private quotaCategoryService: QuotaCategoryService,
        private jobNotificationService: JobNotificationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.quotaCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.quotacategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.jobNotificationService.query()
            .subscribe((res: ResponseWrapper) => { this.jobnotifications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.quotaJobDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.quotaJobDetailsService.update(this.quotaJobDetails), false);
        } else {
            this.subscribeToSaveResponse(
                this.quotaJobDetailsService.create(this.quotaJobDetails), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<QuotaJobDetails>, isCreated: boolean) {
        result.subscribe((res: QuotaJobDetails) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: QuotaJobDetails, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.quotaJobDetails.created'
            : 'jobportalApp.quotaJobDetails.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'quotaJobDetailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackQuotaCategoryById(index: number, item: QuotaCategory) {
        return item.id;
    }

    trackJobNotificationById(index: number, item: JobNotification) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-quota-job-details-popup',
    template: ''
})
export class QuotaJobDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quotaJobDetailsPopupService: QuotaJobDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.quotaJobDetailsPopupService
                    .open(QuotaJobDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.quotaJobDetailsPopupService
                    .open(QuotaJobDetailsDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
