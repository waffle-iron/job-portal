import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { SelectionProcedure } from './selection-procedure.model';
import { SelectionProcedurePopupService } from './selection-procedure-popup.service';
import { SelectionProcedureService } from './selection-procedure.service';

@Component({
    selector: 'jhi-selection-procedure-delete-dialog',
    templateUrl: './selection-procedure-delete-dialog.component.html'
})
export class SelectionProcedureDeleteDialogComponent {

    selectionProcedure: SelectionProcedure;

    constructor(
        private selectionProcedureService: SelectionProcedureService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.selectionProcedureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'selectionProcedureListModification',
                content: 'Deleted an selectionProcedure'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.selectionProcedure.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-selection-procedure-delete-popup',
    template: ''
})
export class SelectionProcedureDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private selectionProcedurePopupService: SelectionProcedurePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.selectionProcedurePopupService
                .open(SelectionProcedureDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
