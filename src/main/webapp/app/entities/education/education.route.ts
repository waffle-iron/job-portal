import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EducationComponent } from './education.component';
import { EducationDetailComponent } from './education-detail.component';
import { EducationPopupComponent } from './education-dialog.component';
import { EducationDeletePopupComponent } from './education-delete-dialog.component';

import { Principal } from '../../shared';

export const educationRoute: Routes = [
    {
        path: 'education',
        component: EducationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.education.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'education/:id',
        component: EducationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.education.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const educationPopupRoute: Routes = [
    {
        path: 'education-new',
        component: EducationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.education.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'education/:id/edit',
        component: EducationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.education.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'education/:id/delete',
        component: EducationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.education.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
