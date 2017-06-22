import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { QuotaCategory } from './quota-category.model';
import { QuotaCategoryService } from './quota-category.service';

@Component({
    selector: 'jhi-quota-category-detail',
    templateUrl: './quota-category-detail.component.html'
})
export class QuotaCategoryDetailComponent implements OnInit, OnDestroy {

    quotaCategory: QuotaCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private quotaCategoryService: QuotaCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInQuotaCategories();
    }

    load(id) {
        this.quotaCategoryService.find(id).subscribe((quotaCategory) => {
            this.quotaCategory = quotaCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQuotaCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'quotaCategoryListModification',
            (response) => this.load(this.quotaCategory.id)
        );
    }
}
