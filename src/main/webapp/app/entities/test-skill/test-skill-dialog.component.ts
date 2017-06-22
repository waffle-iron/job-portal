import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TestSkill } from './test-skill.model';
import { TestSkillPopupService } from './test-skill-popup.service';
import { TestSkillService } from './test-skill.service';

@Component({
    selector: 'jhi-test-skill-dialog',
    templateUrl: './test-skill-dialog.component.html'
})
export class TestSkillDialogComponent implements OnInit {

    testSkill: TestSkill;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private testSkillService: TestSkillService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.testSkill.id !== undefined) {
            this.subscribeToSaveResponse(
                this.testSkillService.update(this.testSkill), false);
        } else {
            this.subscribeToSaveResponse(
                this.testSkillService.create(this.testSkill), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<TestSkill>, isCreated: boolean) {
        result.subscribe((res: TestSkill) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TestSkill, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.testSkill.created'
            : 'jobportalApp.testSkill.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'testSkillListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-test-skill-popup',
    template: ''
})
export class TestSkillPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private testSkillPopupService: TestSkillPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.testSkillPopupService
                    .open(TestSkillDialogComponent, params['id']);
            } else {
                this.modalRef = this.testSkillPopupService
                    .open(TestSkillDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
