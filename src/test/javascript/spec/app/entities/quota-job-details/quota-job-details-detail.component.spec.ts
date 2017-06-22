import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JobportalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { QuotaJobDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/quota-job-details/quota-job-details-detail.component';
import { QuotaJobDetailsService } from '../../../../../../main/webapp/app/entities/quota-job-details/quota-job-details.service';
import { QuotaJobDetails } from '../../../../../../main/webapp/app/entities/quota-job-details/quota-job-details.model';

describe('Component Tests', () => {

    describe('QuotaJobDetails Management Detail Component', () => {
        let comp: QuotaJobDetailsDetailComponent;
        let fixture: ComponentFixture<QuotaJobDetailsDetailComponent>;
        let service: QuotaJobDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JobportalTestModule],
                declarations: [QuotaJobDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    QuotaJobDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(QuotaJobDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuotaJobDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuotaJobDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new QuotaJobDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.quotaJobDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
