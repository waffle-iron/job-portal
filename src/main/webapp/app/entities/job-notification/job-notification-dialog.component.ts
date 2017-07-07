import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { JobNotification } from './job-notification.model';
import { JobNotificationPopupService } from './job-notification-popup.service';
import { JobNotificationService } from './job-notification.service';
import { ClientType, ClientTypeService } from '../client-type';
import { JobSector, JobSectorService } from '../job-sector';
import { JobType, JobTypeService } from '../job-type';
import { Education, EducationService } from '../education';
import { TestSkill, TestSkillService } from '../test-skill';
import { SelectionProcedure, SelectionProcedureService } from '../selection-procedure';
import { Language, LanguageService } from '../language';
import { ResponseWrapper } from '../../shared';
import { EditorComponent } from '../../shared/editor/editor.component'

@Component({
    selector: 'jhi-job-notification-dialog',
    templateUrl: './job-notification-dialog.component.html'
})
export class JobNotificationDialogComponent implements OnInit {

    jobNotification: JobNotification;
    authorities: any[];
    isSaving: boolean;

    clienttypes: ClientType[];

    jobsectors: JobSector[];

    jobtypes: JobType[];

    educations: Education[];

    testskills: TestSkill[];

    selectionprocedures: SelectionProcedure[];

    languages: Language[];
    notificationDateDp: any;
    applicationDeadlineDp: any;

    today = new Date();
    minApplicationDate = {year: this.today.getFullYear(), month: this.today.getMonth()+1, day: this.today.getDate()};
    maxNotificationDate = {year: this.today.getFullYear(), month: this.today.getMonth()+1, day: this.today.getDate()};

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private jobNotificationService: JobNotificationService,
        private clientTypeService: ClientTypeService,
        private jobSectorService: JobSectorService,
        private jobTypeService: JobTypeService,
        private educationService: EducationService,
        private testSkillService: TestSkillService,
        private selectionProcedureService: SelectionProcedureService,
        private languageService: LanguageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.clienttypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.jobSectorService.query()
            .subscribe((res: ResponseWrapper) => { this.jobsectors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.jobTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.jobtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.educationService.query()
            .subscribe((res: ResponseWrapper) => { this.educations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.testSkillService.query()
            .subscribe((res: ResponseWrapper) => { this.testskills = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.selectionProcedureService.query()
            .subscribe((res: ResponseWrapper) => { this.selectionprocedures = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.languageService.query()
            .subscribe((res: ResponseWrapper) => { this.languages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, jobNotification, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                jobNotification[field] = base64Data;
                jobNotification[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jobNotification.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobNotificationService.update(this.jobNotification), false);
        } else {
            this.subscribeToSaveResponse(
                this.jobNotificationService.create(this.jobNotification), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<JobNotification>, isCreated: boolean) {
        result.subscribe((res: JobNotification) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: JobNotification, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jobportalApp.jobNotification.created'
            : 'jobportalApp.jobNotification.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'jobNotificationListModification', content: 'OK'});
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

    trackClientTypeById(index: number, item: ClientType) {
        return item.id;
    }

    trackJobSectorById(index: number, item: JobSector) {
        return item.id;
    }

    trackJobTypeById(index: number, item: JobType) {
        return item.id;
    }

    trackEducationById(index: number, item: Education) {
        return item.id;
    }

    trackTestSkillById(index: number, item: TestSkill) {
        return item.id;
    }

    trackSelectionProcedureById(index: number, item: SelectionProcedure) {
        return item.id;
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-job-notification-popup',
    template: ''
})
export class JobNotificationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobNotificationPopupService: JobNotificationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.jobNotificationPopupService
                    .open(JobNotificationDialogComponent, params['id']);
            } else {
                this.modalRef = this.jobNotificationPopupService
                    .open(JobNotificationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
