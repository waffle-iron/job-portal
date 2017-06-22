import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JobportalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClientTypeDetailComponent } from '../../../../../../main/webapp/app/entities/client-type/client-type-detail.component';
import { ClientTypeService } from '../../../../../../main/webapp/app/entities/client-type/client-type.service';
import { ClientType } from '../../../../../../main/webapp/app/entities/client-type/client-type.model';

describe('Component Tests', () => {

    describe('ClientType Management Detail Component', () => {
        let comp: ClientTypeDetailComponent;
        let fixture: ComponentFixture<ClientTypeDetailComponent>;
        let service: ClientTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JobportalTestModule],
                declarations: [ClientTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClientTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(ClientTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClientType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clientType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
