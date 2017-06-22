import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { TestSkill } from './test-skill.model';
import { TestSkillPopupService } from './test-skill-popup.service';
import { TestSkillService } from './test-skill.service';

@Component({
    selector: 'jhi-test-skill-delete-dialog',
    templateUrl: './test-skill-delete-dialog.component.html'
})
export class TestSkillDeleteDialogComponent {

    testSkill: TestSkill;

    constructor(
        private testSkillService: TestSkillService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.testSkillService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'testSkillListModification',
                content: 'Deleted an testSkill'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jobportalApp.testSkill.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-test-skill-delete-popup',
    template: ''
})
export class TestSkillDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private testSkillPopupService: TestSkillPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.testSkillPopupService
                .open(TestSkillDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
