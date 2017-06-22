import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Education } from './education.model';
import { EducationPopupService } from './education-popup.service';
import { EducationService } from './education.service';

@Component({
    selector: 'jhi-education-delete-dialog',
    templateUrl: './education-delete-dialog.component.html'
})
export class EducationDeleteDialogComponent {

    education: Education;

    constructor(
        private educationService: EducationService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.educationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'educationListModification',
                content: 'Deleted an education'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.education.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-education-delete-popup',
    template: ''
})
export class EducationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private educationPopupService: EducationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.educationPopupService
                .open(EducationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
