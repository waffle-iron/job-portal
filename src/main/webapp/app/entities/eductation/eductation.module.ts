import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JobportalSharedModule } from '../../shared';
import {
    EductationService,
    EductationPopupService,
    EductationComponent,
    EductationDetailComponent,
    EductationDialogComponent,
    EductationPopupComponent,
    EductationDeletePopupComponent,
    EductationDeleteDialogComponent,
    eductationRoute,
    eductationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...eductationRoute,
    ...eductationPopupRoute,
];

@NgModule({
    imports: [
        JobportalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EductationComponent,
        EductationDetailComponent,
        EductationDialogComponent,
        EductationDeleteDialogComponent,
        EductationPopupComponent,
        EductationDeletePopupComponent,
    ],
    entryComponents: [
        EductationComponent,
        EductationDialogComponent,
        EductationPopupComponent,
        EductationDeleteDialogComponent,
        EductationDeletePopupComponent,
    ],
    providers: [
        EductationService,
        EductationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalEductationModule {}
