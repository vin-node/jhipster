import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Location } from './location.model';
import { LocationPopupService } from './location-popup.service';
import { LocationService } from './location.service';
import { Coordinates, CoordinatesService } from '../coordinates';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-location-dialog',
    templateUrl: './location-dialog.component.html'
})
export class LocationDialogComponent implements OnInit {

    location: Location;
    isSaving: boolean;

    coordinates: Coordinates[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private locationService: LocationService,
        private coordinatesService: CoordinatesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.coordinatesService.query()
            .subscribe((res: ResponseWrapper) => { this.coordinates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationService.update(this.location));
        } else {
            this.subscribeToSaveResponse(
                this.locationService.create(this.location));
        }
    }

    private subscribeToSaveResponse(result: Observable<Location>) {
        result.subscribe((res: Location) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Location) {
        this.eventManager.broadcast({ name: 'locationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackCoordinatesById(index: number, item: Coordinates) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-location-popup',
    template: ''
})
export class LocationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationPopupService: LocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.locationPopupService
                    .open(LocationDialogComponent as Component, params['id']);
            } else {
                this.locationPopupService
                    .open(LocationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
