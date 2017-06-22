import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Education } from './education.model';
import { EducationService } from './education.service';

@Component({
    selector: 'jhi-education-detail',
    templateUrl: './education-detail.component.html'
})
export class EducationDetailComponent implements OnInit, OnDestroy {

    education: Education;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private educationService: EducationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEducations();
    }

    load(id) {
        this.educationService.find(id).subscribe((education) => {
            this.education = education;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEducations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'educationListModification',
            (response) => this.load(this.education.id)
        );
    }
}
