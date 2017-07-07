import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { JobNotificationComponent } from './job-notification.component';
import { JobNotificationDetailComponent } from './job-notification-detail.component';
import { JobNotificationPopupComponent } from './job-notification-dialog.component';
import { JobNotificationDeletePopupComponent } from './job-notification-delete-dialog.component';
import { JobNotificationEditComponent } from './job-notification-edit.component';

import { Principal } from '../../shared';

export const jobNotificationRoute: Routes = [
    {
        path: 'job-notification',
        component: JobNotificationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobNotification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-notification/:id',
        component: JobNotificationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobNotification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
              path: 'job-notification/edit/:id',
              component: JobNotificationEditComponent,
              data: {
                  authorities: ['ROLE_USER'],
                  pageTitle: 'jobportalApp.jobNotification.home.title'
              },
              canActivate: [UserRouteAccessService]
              }
];

export const jobNotificationPopupRoute: Routes = [
    {
        path: 'job-notification-new',
        component: JobNotificationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobNotification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-notification/:id/delete',
        component: JobNotificationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.jobNotification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
