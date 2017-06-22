import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { JobSector } from './job-sector.model';
import { JobSectorPopupService } from './job-sector-popup.service';
import { JobSectorService } from './job-sector.service';

@Component({
    selector: 'jhi-job-sector-delete-dialog',
    templateUrl: './job-sector-delete-dialog.component.html'
})
export class JobSectorDeleteDialogComponent {

    jobSector: JobSector;

    constructor(
        private jobSectorService: JobSectorService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobSectorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobSectorListModification',
                content: 'Deleted an jobSector'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.jobSector.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-job-sector-delete-popup',
    template: ''
})
export class JobSectorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobSectorPopupService: JobSectorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.jobSectorPopupService
                .open(JobSectorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
