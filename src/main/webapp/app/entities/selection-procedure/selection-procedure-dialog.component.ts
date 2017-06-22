import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SelectionProcedure } from './selection-procedure.model';
import { SelectionProcedurePopupService } from './selection-procedure-popup.service';
import { SelectionProcedureService } from './selection-procedure.service';
import { JobNotification, JobNotificationService } from '../job-notification';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-selection-procedure-dialog',
    templateUrl: './selection-procedure-dialog.component.html'
})
export class SelectionProcedureDialogComponent implements OnInit {

    selectionProcedure: SelectionProcedure;
    authorities: any[];
    isSaving: boolean;

    jobnotifications: JobNotification[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private selectionProcedureService: SelectionProcedureService,
        private jobNotificationService: JobNotificationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.jobNotificationService.query()
            .subscribe((res: ResponseWrapper) => { this.jobnotifications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.selectionProcedure.id !== undefined) {
            this.subscribeToSaveResponse(
                this.selectionProcedureService.update(this.selectionProcedure), false);
        } else {
            this.subscribeToSaveResponse(
                this.selectionProcedureService.create(this.selectionProcedure), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<SelectionProcedure>, isCreated: boolean) {
        result.subscribe((res: SelectionProcedure) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SelectionProcedure, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.selectionProcedure.created'
            : 'jobportalApp.selectionProcedure.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'selectionProcedureListModification', content: 'OK'});
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

    trackJobNotificationById(index: number, item: JobNotification) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-selection-procedure-popup',
    template: ''
})
export class SelectionProcedurePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private selectionProcedurePopupService: SelectionProcedurePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.selectionProcedurePopupService
                    .open(SelectionProcedureDialogComponent, params['id']);
            } else {
                this.modalRef = this.selectionProcedurePopupService
                    .open(SelectionProcedureDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
