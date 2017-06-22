import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JobportalSharedModule } from '../../shared';
import {
    ClientTypeService,
    ClientTypePopupService,
    ClientTypeComponent,
    ClientTypeDetailComponent,
    ClientTypeDialogComponent,
    ClientTypePopupComponent,
    ClientTypeDeletePopupComponent,
    ClientTypeDeleteDialogComponent,
    clientTypeRoute,
    clientTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...clientTypeRoute,
    ...clientTypePopupRoute,
];

@NgModule({
    imports: [
        JobportalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientTypeComponent,
        ClientTypeDetailComponent,
        ClientTypeDialogComponent,
        ClientTypeDeleteDialogComponent,
        ClientTypePopupComponent,
        ClientTypeDeletePopupComponent,
    ],
    entryComponents: [
        ClientTypeComponent,
        ClientTypeDialogComponent,
        ClientTypePopupComponent,
        ClientTypeDeleteDialogComponent,
        ClientTypeDeletePopupComponent,
    ],
    providers: [
        ClientTypeService,
        ClientTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalClientTypeModule {}
