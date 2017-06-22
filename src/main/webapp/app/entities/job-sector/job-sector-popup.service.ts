import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JobSector } from './job-sector.model';
import { JobSectorService } from './job-sector.service';

@Injectable()
export class JobSectorPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private jobSectorService: JobSectorService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.jobSectorService.find(id).subscribe((jobSector) => {
                this.jobSectorModalRef(component, jobSector);
            });
        } else {
            return this.jobSectorModalRef(component, new JobSector());
        }
    }

    jobSectorModalRef(component: Component, jobSector: JobSector): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.jobSector = jobSector;
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
