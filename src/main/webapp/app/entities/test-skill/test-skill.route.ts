import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TestSkillComponent } from './test-skill.component';
import { TestSkillDetailComponent } from './test-skill-detail.component';
import { TestSkillPopupComponent } from './test-skill-dialog.component';
import { TestSkillDeletePopupComponent } from './test-skill-delete-dialog.component';

import { Principal } from '../../shared';

export const testSkillRoute: Routes = [
    {
        path: 'test-skill',
        component: TestSkillComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.testSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'test-skill/:id',
        component: TestSkillDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.testSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const testSkillPopupRoute: Routes = [
    {
        path: 'test-skill-new',
        component: TestSkillPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.testSkill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-skill/:id/edit',
        component: TestSkillPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.testSkill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-skill/:id/delete',
        component: TestSkillDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobportalApp.testSkill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
