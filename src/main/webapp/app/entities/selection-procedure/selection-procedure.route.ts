import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SelectionProcedureComponent } from './selection-procedure.component';
import { SelectionProcedureDetailComponent } from './selection-procedure-detail.component';
import { SelectionProcedurePopupComponent } from './selection-procedure-dialog.component';
import { SelectionProcedureDeletePopupComponent } from './selection-procedure-delete-dialog.component';

import { Principal } from '../../shared';

export const selectionProcedureRoute: Routes = [
    {
        path: 'selection-procedure',
        component: SelectionProcedureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.selectionProcedure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'selection-procedure/:id',
        component: SelectionProcedureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.selectionProcedure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const selectionProcedurePopupRoute: Routes = [
    {
        path: 'selection-procedure-new',
        component: SelectionProcedurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.selectionProcedure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'selection-procedure/:id/edit',
        component: SelectionProcedurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.selectionProcedure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'selection-procedure/:id/delete',
        component: SelectionProcedureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.selectionProcedure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
