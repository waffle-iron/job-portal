import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JobportalSharedModule } from '../../shared';
import {
    TestSkillService,
    TestSkillPopupService,
    TestSkillComponent,
    TestSkillDetailComponent,
    TestSkillDialogComponent,
    TestSkillPopupComponent,
    TestSkillDeletePopupComponent,
    TestSkillDeleteDialogComponent,
    testSkillRoute,
    testSkillPopupRoute,
} from './';

const ENTITY_STATES = [
    ...testSkillRoute,
    ...testSkillPopupRoute,
];

@NgModule({
    imports: [
        JobportalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TestSkillComponent,
        TestSkillDetailComponent,
        TestSkillDialogComponent,
        TestSkillDeleteDialogComponent,
        TestSkillPopupComponent,
        TestSkillDeletePopupComponent,
    ],
    entryComponents: [
        TestSkillComponent,
        TestSkillDialogComponent,
        TestSkillPopupComponent,
        TestSkillDeleteDialogComponent,
        TestSkillDeletePopupComponent,
    ],
    providers: [
        TestSkillService,
        TestSkillPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalTestSkillModule {}
