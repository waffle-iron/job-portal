import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JobportalSharedModule } from '../../shared';
import {
    QuotaJobDetailsService,
    QuotaJobDetailsPopupService,
    QuotaJobDetailsComponent,
    QuotaJobDetailsDetailComponent,
    QuotaJobDetailsDialogComponent,
    QuotaJobDetailsPopupComponent,
    QuotaJobDetailsDeletePopupComponent,
    QuotaJobDetailsDeleteDialogComponent,
    quotaJobDetailsRoute,
    quotaJobDetailsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...quotaJobDetailsRoute,
    ...quotaJobDetailsPopupRoute,
];

@NgModule({
    imports: [
        JobportalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        QuotaJobDetailsComponent,
        QuotaJobDetailsDetailComponent,
        QuotaJobDetailsDialogComponent,
        QuotaJobDetailsDeleteDialogComponent,
        QuotaJobDetailsPopupComponent,
        QuotaJobDetailsDeletePopupComponent,
    ],
    entryComponents: [
        QuotaJobDetailsComponent,
        QuotaJobDetailsDialogComponent,
        QuotaJobDetailsPopupComponent,
        QuotaJobDetailsDeleteDialogComponent,
        QuotaJobDetailsDeletePopupComponent,
    ],
    providers: [
        QuotaJobDetailsService,
        QuotaJobDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalQuotaJobDetailsModule {}
