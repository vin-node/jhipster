import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    GeoCoordinateService,
    GeoCoordinatePopupService,
    GeoCoordinateComponent,
    GeoCoordinateDetailComponent,
    GeoCoordinateDialogComponent,
    GeoCoordinatePopupComponent,
    GeoCoordinateDeletePopupComponent,
    GeoCoordinateDeleteDialogComponent,
    geoCoordinateRoute,
    geoCoordinatePopupRoute,
} from './';

const ENTITY_STATES = [
    ...geoCoordinateRoute,
    ...geoCoordinatePopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GeoCoordinateComponent,
        GeoCoordinateDetailComponent,
        GeoCoordinateDialogComponent,
        GeoCoordinateDeleteDialogComponent,
        GeoCoordinatePopupComponent,
        GeoCoordinateDeletePopupComponent,
    ],
    entryComponents: [
        GeoCoordinateComponent,
        GeoCoordinateDialogComponent,
        GeoCoordinatePopupComponent,
        GeoCoordinateDeleteDialogComponent,
        GeoCoordinateDeletePopupComponent,
    ],
    providers: [
        GeoCoordinateService,
        GeoCoordinatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppGeoCoordinateModule {}
