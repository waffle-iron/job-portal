import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { QuotaCategory } from './quota-category.model';
import { QuotaCategoryPopupService } from './quota-category-popup.service';
import { QuotaCategoryService } from './quota-category.service';

@Component({
    selector: 'jhi-quota-category-delete-dialog',
    templateUrl: './quota-category-delete-dialog.component.html'
})
export class QuotaCategoryDeleteDialogComponent {

    quotaCategory: QuotaCategory;

    constructor(
        private quotaCategoryService: QuotaCategoryService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quotaCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'quotaCategoryListModification',
                content: 'Deleted an quotaCategory'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.quotaCategory.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-quota-category-delete-popup',
    template: ''
})
export class QuotaCategoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quotaCategoryPopupService: QuotaCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.quotaCategoryPopupService
                .open(QuotaCategoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
