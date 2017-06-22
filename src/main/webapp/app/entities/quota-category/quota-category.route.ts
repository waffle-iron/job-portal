import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { QuotaCategoryComponent } from './quota-category.component';
import { QuotaCategoryDetailComponent } from './quota-category-detail.component';
import { QuotaCategoryPopupComponent } from './quota-category-dialog.component';
import { QuotaCategoryDeletePopupComponent } from './quota-category-delete-dialog.component';

import { Principal } from '../../shared';

export const quotaCategoryRoute: Routes = [
    {
        path: 'quota-category',
        component: QuotaCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'quota-category/:id',
        component: QuotaCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotaCategoryPopupRoute: Routes = [
    {
        path: 'quota-category-new',
        component: QuotaCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quota-category/:id/edit',
        component: QuotaCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quota-category/:id/delete',
        component: QuotaCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.quotaCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
