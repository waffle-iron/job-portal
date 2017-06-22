import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TestSkill } from './test-skill.model';
import { TestSkillService } from './test-skill.service';

@Component({
    selector: 'jhi-test-skill-detail',
    templateUrl: './test-skill-detail.component.html'
})
export class TestSkillDetailComponent implements OnInit, OnDestroy {

    testSkill: TestSkill;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private testSkillService: TestSkillService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTestSkills();
    }

    load(id) {
        this.testSkillService.find(id).subscribe((testSkill) => {
            this.testSkill = testSkill;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTestSkills() {
        this.eventSubscriber = this.eventManager.subscribe(
            'testSkillListModification',
            (response) => this.load(this.testSkill.id)
        );
    }
}
