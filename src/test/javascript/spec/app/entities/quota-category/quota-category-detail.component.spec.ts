import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JobportalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { QuotaCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/quota-category/quota-category-detail.component';
import { QuotaCategoryService } from '../../../../../../main/webapp/app/entities/quota-category/quota-category.service';
import { QuotaCategory } from '../../../../../../main/webapp/app/entities/quota-category/quota-category.model';

describe('Component Tests', () => {

    describe('QuotaCategory Management Detail Component', () => {
        let comp: QuotaCategoryDetailComponent;
        let fixture: ComponentFixture<QuotaCategoryDetailComponent>;
        let service: QuotaCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JobportalTestModule],
                declarations: [QuotaCategoryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    QuotaCategoryService,
                    JhiEventManager
                ]
            }).overrideTemplate(QuotaCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuotaCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuotaCategoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new QuotaCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.quotaCategory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
