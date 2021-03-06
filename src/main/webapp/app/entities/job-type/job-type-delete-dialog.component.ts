import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { JobType } from './job-type.model';
import { JobTypePopupService } from './job-type-popup.service';
import { JobTypeService } from './job-type.service';

@Component({
    selector: 'jhi-job-type-delete-dialog',
    templateUrl: './job-type-delete-dialog.component.html'
})
export class JobTypeDeleteDialogComponent {

    jobType: JobType;

    constructor(
        private jobTypeService: JobTypeService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobTypeListModification',
                content: 'Deleted an jobType'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.jobType.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-job-type-delete-popup',
    template: ''
})
export class JobTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobTypePopupService: JobTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.jobTypePopupService
                .open(JobTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
