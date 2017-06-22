import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JobportalSharedModule } from '../../shared';
import {
    JobSectorService,
    JobSectorPopupService,
    JobSectorComponent,
    JobSectorDetailComponent,
    JobSectorDialogComponent,
    JobSectorPopupComponent,
    JobSectorDeletePopupComponent,
    JobSectorDeleteDialogComponent,
    jobSectorRoute,
    jobSectorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...jobSectorRoute,
    ...jobSectorPopupRoute,
];

@NgModule({
    imports: [
        JobportalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        JobSectorComponent,
        JobSectorDetailComponent,
        JobSectorDialogComponent,
        JobSectorDeleteDialogComponent,
        JobSectorPopupComponent,
        JobSectorDeletePopupComponent,
    ],
    entryComponents: [
        JobSectorComponent,
        JobSectorDialogComponent,
        JobSectorPopupComponent,
        JobSectorDeleteDialogComponent,
        JobSectorDeletePopupComponent,
    ],
    providers: [
        JobSectorService,
        JobSectorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalJobSectorModule {}
