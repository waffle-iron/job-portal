import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JobportalSharedModule } from '../../shared';
import {
    QuotaCategoryService,
    QuotaCategoryPopupService,
    QuotaCategoryComponent,
    QuotaCategoryDetailComponent,
    QuotaCategoryDialogComponent,
    QuotaCategoryPopupComponent,
    QuotaCategoryDeletePopupComponent,
    QuotaCategoryDeleteDialogComponent,
    quotaCategoryRoute,
    quotaCategoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...quotaCategoryRoute,
    ...quotaCategoryPopupRoute,
];

@NgModule({
    imports: [
        JobportalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        QuotaCategoryComponent,
        QuotaCategoryDetailComponent,
        QuotaCategoryDialogComponent,
        QuotaCategoryDeleteDialogComponent,
        QuotaCategoryPopupComponent,
        QuotaCategoryDeletePopupComponent,
    ],
    entryComponents: [
        QuotaCategoryComponent,
        QuotaCategoryDialogComponent,
        QuotaCategoryPopupComponent,
        QuotaCategoryDeleteDialogComponent,
        QuotaCategoryDeletePopupComponent,
    ],
    providers: [
        QuotaCategoryService,
        QuotaCategoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalQuotaCategoryModule {}
