import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobType } from './job-type.model';
import { JobTypePopupService } from './job-type-popup.service';
import { JobTypeService } from './job-type.service';

@Component({
    selector: 'jhi-job-type-dialog',
    templateUrl: './job-type-dialog.component.html'
})
export class JobTypeDialogComponent implements OnInit {

    jobType: JobType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private jobTypeService: JobTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jobType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobTypeService.update(this.jobType), false);
        } else {
            this.subscribeToSaveResponse(
                this.jobTypeService.create(this.jobType), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<JobType>, isCreated: boolean) {
        result.subscribe((res: JobType) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: JobType, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.jobType.created'
            : 'jobportalApp.jobType.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'jobTypeListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-job-type-popup',
    template: ''
})
export class JobTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobTypePopupService: JobTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.jobTypePopupService
                    .open(JobTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.jobTypePopupService
                    .open(JobTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
