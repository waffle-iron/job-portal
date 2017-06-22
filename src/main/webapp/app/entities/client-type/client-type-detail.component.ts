import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ClientType } from './client-type.model';
import { ClientTypeService } from './client-type.service';

@Component({
    selector: 'jhi-client-type-detail',
    templateUrl: './client-type-detail.component.html'
})
export class ClientTypeDetailComponent implements OnInit, OnDestroy {

    clientType: ClientType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clientTypeService: ClientTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClientTypes();
    }

    load(id) {
        this.clientTypeService.find(id).subscribe((clientType) => {
            this.clientType = clientType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClientTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clientTypeListModification',
            (response) => this.load(this.clientType.id)
        );
    }
}
