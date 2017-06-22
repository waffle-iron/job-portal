import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Eductation } from './eductation.model';
import { EductationPopupService } from './eductation-popup.service';
import { EductationService } from './eductation.service';

@Component({
    selector: 'jhi-eductation-delete-dialog',
    templateUrl: './eductation-delete-dialog.component.html'
})
export class EductationDeleteDialogComponent {

    eductation: Eductation;

    constructor(
        private eductationService: EductationService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eductationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'eductationListModification',
                content: 'Deleted an eductation'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.eductation.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-eductation-delete-popup',
    template: ''
})
export class EductationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private eductationPopupService: EductationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.eductationPopupService
                .open(EductationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
