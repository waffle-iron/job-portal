import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JobNotification } from './job-notification.model';
import { JobNotificationService } from './job-notification.service';

@Injectable()
export class JobNotificationPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private jobNotificationService: JobNotificationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.jobNotificationService.find(id).subscribe((jobNotification) => {
                if (jobNotification.notificationDate) {
                    jobNotification.notificationDate = {
                        year: jobNotification.notificationDate.getFullYear(),
                        month: jobNotification.notificationDate.getMonth() + 1,
                        day: jobNotification.notificationDate.getDate()
                    };
                }
                if (jobNotification.applicationDeadline) {
                    jobNotification.applicationDeadline = {
                        year: jobNotification.applicationDeadline.getFullYear(),
                        month: jobNotification.applicationDeadline.getMonth() + 1,
                        day: jobNotification.applicationDeadline.getDate()
                    };
                }
                this.jobNotificationModalRef(component, jobNotification);
            });
        } else {
            return this.jobNotificationModalRef(component, new JobNotification());
        }
    }

    jobNotificationModalRef(component: Component, jobNotification: JobNotification): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.jobNotification = jobNotification;
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
