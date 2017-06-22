import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SelectionProcedure } from './selection-procedure.model';
import { SelectionProcedureService } from './selection-procedure.service';

@Injectable()
export class SelectionProcedurePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private selectionProcedureService: SelectionProcedureService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.selectionProcedureService.find(id).subscribe((selectionProcedure) => {
                this.selectionProcedureModalRef(component, selectionProcedure);
            });
        } else {
            return this.selectionProcedureModalRef(component, new SelectionProcedure());
        }
    }

    selectionProcedureModalRef(component: Component, selectionProcedure: SelectionProcedure): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.selectionProcedure = selectionProcedure;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
