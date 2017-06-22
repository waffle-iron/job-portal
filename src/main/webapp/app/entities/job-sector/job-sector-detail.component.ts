import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { JobSector } from './job-sector.model';
import { JobSectorService } from './job-sector.service';

@Component({
    selector: 'jhi-job-sector-detail',
    templateUrl: './job-sector-detail.component.html'
})
export class JobSectorDetailComponent implements OnInit, OnDestroy {

    jobSector: JobSector;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jobSectorService: JobSectorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobSectors();
    }

    load(id) {
        this.jobSectorService.find(id).subscribe((jobSector) => {
            this.jobSector = jobSector;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobSectors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobSectorListModification',
            (response) => this.load(this.jobSector.id)
        );
    }
}
