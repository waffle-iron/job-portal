import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobSector } from './job-sector.model';
import { JobSectorPopupService } from './job-sector-popup.service';
import { JobSectorService } from './job-sector.service';

@Component({
    selector: 'jhi-job-sector-dialog',
    templateUrl: './job-sector-dialog.component.html'
})
export class JobSectorDialogComponent implements OnInit {

    jobSector: JobSector;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private jobSectorService: JobSectorService,
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
        if (this.jobSector.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobSectorService.update(this.jobSector), false);
        } else {
            this.subscribeToSaveResponse(
                this.jobSectorService.create(this.jobSector), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<JobSector>, isCreated: boolean) {
        result.subscribe((res: JobSector) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: JobSector, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.jobSector.created'
            : 'jobportalApp.jobSector.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'jobSectorListModification', content: 'OK'});
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
    selector: 'jhi-job-sector-popup',
    template: ''
})
export class JobSectorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobSectorPopupService: JobSectorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.jobSectorPopupService
                    .open(JobSectorDialogComponent, params['id']);
            } else {
                this.modalRef = this.jobSectorPopupService
                    .open(JobSectorDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
