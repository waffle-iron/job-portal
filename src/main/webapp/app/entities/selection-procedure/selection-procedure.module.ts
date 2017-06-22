import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JobportalSharedModule } from '../../shared';
import {
    SelectionProcedureService,
    SelectionProcedurePopupService,
    SelectionProcedureComponent,
    SelectionProcedureDetailComponent,
    SelectionProcedureDialogComponent,
    SelectionProcedurePopupComponent,
    SelectionProcedureDeletePopupComponent,
    SelectionProcedureDeleteDialogComponent,
    selectionProcedureRoute,
    selectionProcedurePopupRoute,
} from './';

const ENTITY_STATES = [
    ...selectionProcedureRoute,
    ...selectionProcedurePopupRoute,
];

@NgModule({
    imports: [
        JobportalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SelectionProcedureComponent,
        SelectionProcedureDetailComponent,
        SelectionProcedureDialogComponent,
        SelectionProcedureDeleteDialogComponent,
        SelectionProcedurePopupComponent,
        SelectionProcedureDeletePopupComponent,
    ],
    entryComponents: [
        SelectionProcedureComponent,
        SelectionProcedureDialogComponent,
        SelectionProcedurePopupComponent,
        SelectionProcedureDeleteDialogComponent,
        SelectionProcedureDeletePopupComponent,
    ],
    providers: [
        SelectionProcedureService,
        SelectionProcedurePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalSelectionProcedureModule {}
