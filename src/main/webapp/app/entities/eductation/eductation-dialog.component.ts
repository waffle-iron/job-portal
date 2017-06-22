import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Eductation } from './eductation.model';
import { EductationPopupService } from './eductation-popup.service';
import { EductationService } from './eductation.service';

@Component({
    selector: 'jhi-eductation-dialog',
    templateUrl: './eductation-dialog.component.html'
})
export class EductationDialogComponent implements OnInit {

    eductation: Eductation;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eductationService: EductationService,
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
        if (this.eductation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.eductationService.update(this.eductation), false);
        } else {
            this.subscribeToSaveResponse(
                this.eductationService.create(this.eductation), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Eductation>, isCreated: boolean) {
        result.subscribe((res: Eductation) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Eductation, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.eductation.created'
            : 'jobportalApp.eductation.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'eductationListModification', content: 'OK'});
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
    selector: 'jhi-eductation-popup',
    template: ''
})
export class EductationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private eductationPopupService: EductationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.eductationPopupService
                    .open(EductationDialogComponent, params['id']);
            } else {
                this.modalRef = this.eductationPopupService
                    .open(EductationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
