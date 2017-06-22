import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JobportalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TestSkillDetailComponent } from '../../../../../../main/webapp/app/entities/test-skill/test-skill-detail.component';
import { TestSkillService } from '../../../../../../main/webapp/app/entities/test-skill/test-skill.service';
import { TestSkill } from '../../../../../../main/webapp/app/entities/test-skill/test-skill.model';

describe('Component Tests', () => {

    describe('TestSkill Management Detail Component', () => {
        let comp: TestSkillDetailComponent;
        let fixture: ComponentFixture<TestSkillDetailComponent>;
        let service: TestSkillService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JobportalTestModule],
                declarations: [TestSkillDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TestSkillService,
                    JhiEventManager
                ]
            }).overrideTemplate(TestSkillDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TestSkillDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestSkillService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TestSkill(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.testSkill).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
