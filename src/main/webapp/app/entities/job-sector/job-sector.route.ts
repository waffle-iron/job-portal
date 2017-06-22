import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { JobSectorComponent } from './job-sector.component';
import { JobSectorDetailComponent } from './job-sector-detail.component';
import { JobSectorPopupComponent } from './job-sector-dialog.component';
import { JobSectorDeletePopupComponent } from './job-sector-delete-dialog.component';

import { Principal } from '../../shared';

export const jobSectorRoute: Routes = [
    {
        path: 'job-sector',
        component: JobSectorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobSector.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-sector/:id',
        component: JobSectorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobSector.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobSectorPopupRoute: Routes = [
    {
        path: 'job-sector-new',
        component: JobSectorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobSector.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-sector/:id/edit',
        component: JobSectorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobSector.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-sector/:id/delete',
        component: JobSectorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobSector.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
