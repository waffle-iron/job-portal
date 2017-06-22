import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { QuotaCategory } from './quota-category.model';
import { QuotaCategoryPopupService } from './quota-category-popup.service';
import { QuotaCategoryService } from './quota-category.service';

@Component({
    selector: 'jhi-quota-category-dialog',
    templateUrl: './quota-category-dialog.component.html'
})
export class QuotaCategoryDialogComponent implements OnInit {

    quotaCategory: QuotaCategory;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private quotaCategoryService: QuotaCategoryService,
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
        if (this.quotaCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.quotaCategoryService.update(this.quotaCategory), false);
        } else {
            this.subscribeToSaveResponse(
                this.quotaCategoryService.create(this.quotaCategory), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<QuotaCategory>, isCreated: boolean) {
        result.subscribe((res: QuotaCategory) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: QuotaCategory, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.quotaCategory.created'
            : 'jobportalApp.quotaCategory.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'quotaCategoryListModification', content: 'OK'});
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
    selector: 'jhi-quota-category-popup',
    template: ''
})
export class QuotaCategoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quotaCategoryPopupService: QuotaCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.quotaCategoryPopupService
                    .open(QuotaCategoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.quotaCategoryPopupService
                    .open(QuotaCategoryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
