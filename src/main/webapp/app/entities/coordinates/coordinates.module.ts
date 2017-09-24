import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    CoordinatesService,
    CoordinatesPopupService,
    CoordinatesComponent,
    CoordinatesDetailComponent,
    CoordinatesDialogComponent,
    CoordinatesPopupComponent,
    CoordinatesDeletePopupComponent,
    CoordinatesDeleteDialogComponent,
    coordinatesRoute,
    coordinatesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...coordinatesRoute,
    ...coordinatesPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CoordinatesComponent,
        CoordinatesDetailComponent,
        CoordinatesDialogComponent,
        CoordinatesDeleteDialogComponent,
        CoordinatesPopupComponent,
        CoordinatesDeletePopupComponent,
    ],
    entryComponents: [
        CoordinatesComponent,
        CoordinatesDialogComponent,
        CoordinatesPopupComponent,
        CoordinatesDeleteDialogComponent,
        CoordinatesDeletePopupComponent,
    ],
    providers: [
        CoordinatesService,
        CoordinatesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppCoordinatesModule {}
