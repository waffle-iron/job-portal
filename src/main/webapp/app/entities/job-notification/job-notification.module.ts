import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JobportalSharedModule } from '../../shared';
import {
    JobNotificationService,
    JobNotificationPopupService,
    JobNotificationComponent,
    JobNotificationDetailComponent,
    JobNotificationDialogComponent,
    JobNotificationPopupComponent,
    JobNotificationDeletePopupComponent,
    JobNotificationDeleteDialogComponent,
    jobNotificationRoute,
    jobNotificationPopupRoute,
    JobNotificationEditComponent,
} from './';

const ENTITY_STATES = [
    ...jobNotificationRoute,
    ...jobNotificationPopupRoute,
];

@NgModule({
    imports: [
        JobportalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        JobNotificationComponent,
        JobNotificationDetailComponent,
        JobNotificationDialogComponent,
        JobNotificationDeleteDialogComponent,
        JobNotificationPopupComponent,
        JobNotificationDeletePopupComponent,
        JobNotificationEditComponent,
    ],
    entryComponents: [
        JobNotificationComponent,
        JobNotificationDialogComponent,
        JobNotificationPopupComponent,
        JobNotificationDeleteDialogComponent,
        JobNotificationDeletePopupComponent,
        JobNotificationEditComponent,
    ],
    providers: [
        JobNotificationService,
        JobNotificationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalJobNotificationModule {}
