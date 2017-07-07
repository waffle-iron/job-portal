import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable, Subscription } from 'rxjs/Rx';

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


@Component({
    selector: 'jhi-job-notification-detail',
    templateUrl: './job-notification-edit.component.html'
})
export class JobNotificationEditComponent implements OnInit, OnDestroy {

     jobNotification: JobNotification;
        private subscription: Subscription;
        private eventSubscriber: Subscription;

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

        constructor(
           // private eventManager: JhiEventManager,
            //private dataUtils: JhiDataUtils,
           // private jobNotificationService: JobNotificationService,
          // public activeModal: NgbActiveModal,
            private route: ActivatedRoute,


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
            this.subscription = this.route.params.subscribe((params) => {
                this.load(params['id']);
            });
            this.registerChangeInJobNotifications();
            //
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

        load(id) {
            this.jobNotificationService.find(id).subscribe((jobNotification) => {
                this.jobNotification = jobNotification;
            });
        }
        byteSize(field) {
            return this.dataUtils.byteSize(field);
        }

        openFile(contentType, field) {
            return this.dataUtils.openFile(contentType, field);
        }
        previousState() {
            window.history.back();
        }

        ngOnDestroy() {
            this.subscription.unsubscribe();
            this.eventManager.destroy(this.eventSubscriber);
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

        registerChangeInJobNotifications() {
            this.eventSubscriber = this.eventManager.subscribe(
                'jobNotificationListModification',
                (response) => this.load(this.jobNotification.id)
            );
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
            //this.isSaving = false;
            //this.activeModal.dismiss(result);
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
