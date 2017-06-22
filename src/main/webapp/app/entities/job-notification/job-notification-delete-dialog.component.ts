import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { JobNotification } from './job-notification.model';
import { JobNotificationPopupService } from './job-notification-popup.service';
import { JobNotificationService } from './job-notification.service';

@Component({
    selector: 'jhi-job-notification-delete-dialog',
    templateUrl: './job-notification-delete-dialog.component.html'
})
export class JobNotificationDeleteDialogComponent {

    jobNotification: JobNotification;

    constructor(
        private jobNotificationService: JobNotificationService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobNotificationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobNotificationListModification',
                content: 'Deleted an jobNotification'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.jobNotification.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-job-notification-delete-popup',
    template: ''
})
export class JobNotificationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobNotificationPopupService: JobNotificationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.jobNotificationPopupService
                .open(JobNotificationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
