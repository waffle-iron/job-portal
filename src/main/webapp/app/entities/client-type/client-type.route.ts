import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ClientTypeComponent } from './client-type.component';
import { ClientTypeDetailComponent } from './client-type-detail.component';
import { ClientTypePopupComponent } from './client-type-dialog.component';
import { ClientTypeDeletePopupComponent } from './client-type-delete-dialog.component';

import { Principal } from '../../shared';

export const clientTypeRoute: Routes = [
    {
        path: 'client-type',
        component: ClientTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.clientType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'client-type/:id',
        component: ClientTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.clientType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientTypePopupRoute: Routes = [
    {
        path: 'client-type-new',
        component: ClientTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.clientType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-type/:id/edit',
        component: ClientTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.clientType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-type/:id/delete',
        component: ClientTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.clientType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
