import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SelectionProcedure } from './selection-procedure.model';
import { SelectionProcedureService } from './selection-procedure.service';

@Component({
    selector: 'jhi-selection-procedure-detail',
    templateUrl: './selection-procedure-detail.component.html'
})
export class SelectionProcedureDetailComponent implements OnInit, OnDestroy {

    selectionProcedure: SelectionProcedure;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private selectionProcedureService: SelectionProcedureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSelectionProcedures();
    }

    load(id) {
        this.selectionProcedureService.find(id).subscribe((selectionProcedure) => {
            this.selectionProcedure = selectionProcedure;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSelectionProcedures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'selectionProcedureListModification',
            (response) => this.load(this.selectionProcedure.id)
        );
    }
}
