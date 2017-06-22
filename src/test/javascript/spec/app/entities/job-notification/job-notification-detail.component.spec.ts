import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JobportalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JobNotificationDetailComponent } from '../../../../../../main/webapp/app/entities/job-notification/job-notification-detail.component';
import { JobNotificationService } from '../../../../../../main/webapp/app/entities/job-notification/job-notification.service';
import { JobNotification } from '../../../../../../main/webapp/app/entities/job-notification/job-notification.model';

describe('Component Tests', () => {

    describe('JobNotification Management Detail Component', () => {
        let comp: JobNotificationDetailComponent;
        let fixture: ComponentFixture<JobNotificationDetailComponent>;
        let service: JobNotificationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JobportalTestModule],
                declarations: [JobNotificationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JobNotificationService,
                    JhiEventManager
                ]
            }).overrideTemplate(JobNotificationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobNotificationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobNotificationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new JobNotification(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.jobNotification).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
