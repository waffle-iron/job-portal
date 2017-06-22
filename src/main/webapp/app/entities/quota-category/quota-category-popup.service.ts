import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { QuotaCategory } from './quota-category.model';
import { QuotaCategoryService } from './quota-category.service';

@Injectable()
export class QuotaCategoryPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private quotaCategoryService: QuotaCategoryService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.quotaCategoryService.find(id).subscribe((quotaCategory) => {
                this.quotaCategoryModalRef(component, quotaCategory);
            });
        } else {
            return this.quotaCategoryModalRef(component, new QuotaCategory());
        }
    }

    quotaCategoryModalRef(component: Component, quotaCategory: QuotaCategory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.quotaCategory = quotaCategory;
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
