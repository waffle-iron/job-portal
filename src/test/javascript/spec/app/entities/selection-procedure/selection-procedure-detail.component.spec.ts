import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JobportalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SelectionProcedureDetailComponent } from '../../../../../../main/webapp/app/entities/selection-procedure/selection-procedure-detail.component';
import { SelectionProcedureService } from '../../../../../../main/webapp/app/entities/selection-procedure/selection-procedure.service';
import { SelectionProcedure } from '../../../../../../main/webapp/app/entities/selection-procedure/selection-procedure.model';

describe('Component Tests', () => {

    describe('SelectionProcedure Management Detail Component', () => {
        let comp: SelectionProcedureDetailComponent;
        let fixture: ComponentFixture<SelectionProcedureDetailComponent>;
        let service: SelectionProcedureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JobportalTestModule],
                declarations: [SelectionProcedureDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SelectionProcedureService,
                    JhiEventManager
                ]
            }).overrideTemplate(SelectionProcedureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SelectionProcedureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SelectionProcedureService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SelectionProcedure(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.selectionProcedure).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
