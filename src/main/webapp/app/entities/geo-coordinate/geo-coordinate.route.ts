import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GeoCoordinateComponent } from './geo-coordinate.component';
import { GeoCoordinateDetailComponent } from './geo-coordinate-detail.component';
import { GeoCoordinatePopupComponent } from './geo-coordinate-dialog.component';
import { GeoCoordinateDeletePopupComponent } from './geo-coordinate-delete-dialog.component';

export const geoCoordinateRoute: Routes = [
    {
        path: 'geo-coordinate',
        component: GeoCoordinateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeoCoordinates'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'geo-coordinate/:id',
        component: GeoCoordinateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeoCoordinates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const geoCoordinatePopupRoute: Routes = [
    {
        path: 'geo-coordinate-new',
        component: GeoCoordinatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeoCoordinates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'geo-coordinate/:id/edit',
        component: GeoCoordinatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeoCoordinates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'geo-coordinate/:id/delete',
        component: GeoCoordinateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeoCoordinates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
