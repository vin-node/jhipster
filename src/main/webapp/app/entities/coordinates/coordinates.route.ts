import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CoordinatesComponent } from './coordinates.component';
import { CoordinatesDetailComponent } from './coordinates-detail.component';
import { CoordinatesPopupComponent } from './coordinates-dialog.component';
import { CoordinatesDeletePopupComponent } from './coordinates-delete-dialog.component';

export const coordinatesRoute: Routes = [
    {
        path: 'coordinates',
        component: CoordinatesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Coordinates'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'coordinates/:id',
        component: CoordinatesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Coordinates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coordinatesPopupRoute: Routes = [
    {
        path: 'coordinates-new',
        component: CoordinatesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Coordinates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coordinates/:id/edit',
        component: CoordinatesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Coordinates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coordinates/:id/delete',
        component: CoordinatesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Coordinates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
