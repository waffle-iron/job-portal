import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { QuotaJobDetails } from './quota-job-details.model';
import { QuotaJobDetailsPopupService } from './quota-job-details-popup.service';
import { QuotaJobDetailsService } from './quota-job-details.service';

@Component({
    selector: 'jhi-quota-job-details-delete-dialog',
    templateUrl: './quota-job-details-delete-dialog.component.html'
})
export class QuotaJobDetailsDeleteDialogComponent {

    quotaJobDetails: QuotaJobDetails;

    constructor(
        private quotaJobDetailsService: QuotaJobDetailsService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quotaJobDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'quotaJobDetailsListModification',
                content: 'Deleted an quotaJobDetails'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.quotaJobDetails.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-quota-job-details-delete-popup',
    template: ''
})
export class QuotaJobDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quotaJobDetailsPopupService: QuotaJobDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.quotaJobDetailsPopupService
                .open(QuotaJobDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
