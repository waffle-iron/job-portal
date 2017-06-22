import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { QuotaJobDetails } from './quota-job-details.model';
import { QuotaJobDetailsService } from './quota-job-details.service';

@Injectable()
export class QuotaJobDetailsPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private quotaJobDetailsService: QuotaJobDetailsService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.quotaJobDetailsService.find(id).subscribe((quotaJobDetails) => {
                if (quotaJobDetails.bornBefore) {
                    quotaJobDetails.bornBefore = {
                        year: quotaJobDetails.bornBefore.getFullYear(),
                        month: quotaJobDetails.bornBefore.getMonth() + 1,
                        day: quotaJobDetails.bornBefore.getDate()
                    };
                }
                if (quotaJobDetails.bornAfter) {
                    quotaJobDetails.bornAfter = {
                        year: quotaJobDetails.bornAfter.getFullYear(),
                        month: quotaJobDetails.bornAfter.getMonth() + 1,
                        day: quotaJobDetails.bornAfter.getDate()
                    };
                }
                this.quotaJobDetailsModalRef(component, quotaJobDetails);
            });
        } else {
            return this.quotaJobDetailsModalRef(component, new QuotaJobDetails());
        }
    }

    quotaJobDetailsModalRef(component: Component, quotaJobDetails: QuotaJobDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.quotaJobDetails = quotaJobDetails;
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
