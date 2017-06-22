import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { ClientType } from './client-type.model';
import { ClientTypePopupService } from './client-type-popup.service';
import { ClientTypeService } from './client-type.service';

@Component({
    selector: 'jhi-client-type-delete-dialog',
    templateUrl: './client-type-delete-dialog.component.html'
})
export class ClientTypeDeleteDialogComponent {

    clientType: ClientType;

    constructor(
        private clientTypeService: ClientTypeService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clientTypeListModification',
                content: 'Deleted an clientType'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.clientType.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-client-type-delete-popup',
    template: ''
})
export class ClientTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientTypePopupService: ClientTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clientTypePopupService
                .open(ClientTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
