import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JobportalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JobSectorDetailComponent } from '../../../../../../main/webapp/app/entities/job-sector/job-sector-detail.component';
import { JobSectorService } from '../../../../../../main/webapp/app/entities/job-sector/job-sector.service';
import { JobSector } from '../../../../../../main/webapp/app/entities/job-sector/job-sector.model';

describe('Component Tests', () => {

    describe('JobSector Management Detail Component', () => {
        let comp: JobSectorDetailComponent;
        let fixture: ComponentFixture<JobSectorDetailComponent>;
        let service: JobSectorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JobportalTestModule],
                declarations: [JobSectorDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JobSectorService,
                    JhiEventManager
                ]
            }).overrideTemplate(JobSectorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobSectorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobSectorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new JobSector(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.jobSector).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
