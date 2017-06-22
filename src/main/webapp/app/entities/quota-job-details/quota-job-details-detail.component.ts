import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { QuotaJobDetails } from './quota-job-details.model';
import { QuotaJobDetailsService } from './quota-job-details.service';

@Component({
    selector: 'jhi-quota-job-details-detail',
    templateUrl: './quota-job-details-detail.component.html'
})
export class QuotaJobDetailsDetailComponent implements OnInit, OnDestroy {

    quotaJobDetails: QuotaJobDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private quotaJobDetailsService: QuotaJobDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInQuotaJobDetails();
    }

    load(id) {
        this.quotaJobDetailsService.find(id).subscribe((quotaJobDetails) => {
            this.quotaJobDetails = quotaJobDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQuotaJobDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'quotaJobDetailsListModification',
            (response) => this.load(this.quotaJobDetails.id)
        );
    }
}
