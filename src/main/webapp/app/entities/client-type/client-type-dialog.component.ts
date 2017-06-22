import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ClientType } from './client-type.model';
import { ClientTypePopupService } from './client-type-popup.service';
import { ClientTypeService } from './client-type.service';

@Component({
    selector: 'jhi-client-type-dialog',
    templateUrl: './client-type-dialog.component.html'
})
export class ClientTypeDialogComponent implements OnInit {

    clientType: ClientType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private clientTypeService: ClientTypeService,
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
        if (this.clientType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientTypeService.update(this.clientType), false);
        } else {
            this.subscribeToSaveResponse(
                this.clientTypeService.create(this.clientType), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ClientType>, isCreated: boolean) {
        result.subscribe((res: ClientType) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClientType, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.clientType.created'
            : 'jobportalApp.clientType.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'clientTypeListModification', content: 'OK'});
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
    selector: 'jhi-client-type-popup',
    template: ''
})
export class ClientTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientTypePopupService: ClientTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientTypePopupService
                    .open(ClientTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientTypePopupService
                    .open(ClientTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
