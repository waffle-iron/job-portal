import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EductationComponent } from './eductation.component';
import { EductationDetailComponent } from './eductation-detail.component';
import { EductationPopupComponent } from './eductation-dialog.component';
import { EductationDeletePopupComponent } from './eductation-delete-dialog.component';

import { Principal } from '../../shared';

export const eductationRoute: Routes = [
    {
        path: 'eductation',
        component: EductationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.eductation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'eductation/:id',
        component: EductationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.eductation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eductationPopupRoute: Routes = [
    {
        path: 'eductation-new',
        component: EductationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.eductation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'eductation/:id/edit',
        component: EductationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.eductation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'eductation/:id/delete',
        component: EductationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.eductation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
