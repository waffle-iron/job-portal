import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Eductation } from './eductation.model';
import { EductationService } from './eductation.service';

@Component({
    selector: 'jhi-eductation-detail',
    templateUrl: './eductation-detail.component.html'
})
export class EductationDetailComponent implements OnInit, OnDestroy {

    eductation: Eductation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private eductationService: EductationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEductations();
    }

    load(id) {
        this.eductationService.find(id).subscribe((eductation) => {
            this.eductation = eductation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEductations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'eductationListModification',
            (response) => this.load(this.eductation.id)
        );
    }
}
