import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Language } from './language.model';
import { LanguagePopupService } from './language-popup.service';
import { LanguageService } from './language.service';

@Component({
    selector: 'jhi-language-dialog',
    templateUrl: './language-dialog.component.html'
})
export class LanguageDialogComponent implements OnInit {

    language: Language;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private languageService: LanguageService,
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
        if (this.language.id !== undefined) {
            this.subscribeToSaveResponse(
                this.languageService.update(this.language), false);
        } else {
            this.subscribeToSaveResponse(
                this.languageService.create(this.language), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Language>, isCreated: boolean) {
        result.subscribe((res: Language) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Language, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.language.created'
            : 'jobportalApp.language.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'languageListModification', content: 'OK'});
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
    selector: 'jhi-language-popup',
    template: ''
})
export class LanguagePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private languagePopupService: LanguagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.languagePopupService
                    .open(LanguageDialogComponent, params['id']);
            } else {
                this.modalRef = this.languagePopupService
                    .open(LanguageDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
