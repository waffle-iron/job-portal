import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JobportalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EductationDetailComponent } from '../../../../../../main/webapp/app/entities/eductation/eductation-detail.component';
import { EductationService } from '../../../../../../main/webapp/app/entities/eductation/eductation.service';
import { Eductation } from '../../../../../../main/webapp/app/entities/eductation/eductation.model';

describe('Component Tests', () => {

    describe('Eductation Management Detail Component', () => {
        let comp: EductationDetailComponent;
        let fixture: ComponentFixture<EductationDetailComponent>;
        let service: EductationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JobportalTestModule],
                declarations: [EductationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EductationService,
                    JhiEventManager
                ]
            }).overrideTemplate(EductationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EductationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EductationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Eductation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.eductation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
