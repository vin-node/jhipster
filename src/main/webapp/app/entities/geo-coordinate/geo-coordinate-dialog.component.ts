import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GeoCoordinate } from './geo-coordinate.model';
import { GeoCoordinatePopupService } from './geo-coordinate-popup.service';
import { GeoCoordinateService } from './geo-coordinate.service';
import { Location, LocationService } from '../location';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-geo-coordinate-dialog',
    templateUrl: './geo-coordinate-dialog.component.html'
})
export class GeoCoordinateDialogComponent implements OnInit {

    geoCoordinate: GeoCoordinate;
    isSaving: boolean;

    locations: Location[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private geoCoordinateService: GeoCoordinateService,
        private locationService: LocationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService.query()
            .subscribe((res: ResponseWrapper) => { this.locations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.geoCoordinate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.geoCoordinateService.update(this.geoCoordinate));
        } else {
            this.subscribeToSaveResponse(
                this.geoCoordinateService.create(this.geoCoordinate));
        }
    }

    private subscribeToSaveResponse(result: Observable<GeoCoordinate>) {
        result.subscribe((res: GeoCoordinate) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GeoCoordinate) {
        this.eventManager.broadcast({ name: 'geoCoordinateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-geo-coordinate-popup',
    template: ''
})
export class GeoCoordinatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private geoCoordinatePopupService: GeoCoordinatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.geoCoordinatePopupService
                    .open(GeoCoordinateDialogComponent as Component, params['id']);
            } else {
                this.geoCoordinatePopupService
                    .open(GeoCoordinateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
