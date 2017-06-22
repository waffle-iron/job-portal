import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { JobNotification } from './job-notification.model';
import { JobNotificationService } from './job-notification.service';

@Component({
    selector: 'jhi-job-notification-detail',
    templateUrl: './job-notification-detail.component.html'
})
export class JobNotificationDetailComponent implements OnInit, OnDestroy {

    jobNotification: JobNotification;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private jobNotificationService: JobNotificationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobNotifications();
    }

    load(id) {
        this.jobNotificationService.find(id).subscribe((jobNotification) => {
            this.jobNotification = jobNotification;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobNotifications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobNotificationListModification',
            (response) => this.load(this.jobNotification.id)
        );
    }
}
