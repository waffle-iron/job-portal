import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Education } from './education.model';
import { EducationPopupService } from './education-popup.service';
import { EducationService } from './education.service';

@Component({
    selector: 'jhi-education-dialog',
    templateUrl: './education-dialog.component.html'
})
export class EducationDialogComponent implements OnInit {

    education: Education;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private educationService: EducationService,
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
        if (this.education.id !== undefined) {
            this.subscribeToSaveResponse(
                this.educationService.update(this.education), false);
        } else {
            this.subscribeToSaveResponse(
                this.educationService.create(this.education), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Education>, isCreated: boolean) {
        result.subscribe((res: Education) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Education, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.education.created'
            : 'jobportalApp.education.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'educationListModification', content: 'OK'});
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
    selector: 'jhi-education-popup',
    template: ''
})
export class EducationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private educationPopupService: EducationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.educationPopupService
                    .open(EducationDialogComponent, params['id']);
            } else {
                this.modalRef = this.educationPopupService
                    .open(EducationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
