import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { QuotaJobDetailsComponent } from './quota-job-details.component';
import { QuotaJobDetailsDetailComponent } from './quota-job-details-detail.component';
import { QuotaJobDetailsPopupComponent } from './quota-job-details-dialog.component';
import { QuotaJobDetailsDeletePopupComponent } from './quota-job-details-delete-dialog.component';

import { Principal } from '../../shared';

export const quotaJobDetailsRoute: Routes = [
    {
        path: 'quota-job-details',
        component: QuotaJobDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaJobDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'quota-job-details/:id',
        component: QuotaJobDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaJobDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotaJobDetailsPopupRoute: Routes = [
    {
        path: 'quota-job-details-new',
        component: QuotaJobDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaJobDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quota-job-details/:id/edit',
        component: QuotaJobDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaJobDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quota-job-details/:id/delete',
        component: QuotaJobDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaJobDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quota-job-details-new/:id',
        component: QuotaJobDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaJobDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
        }
];
