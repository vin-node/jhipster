import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Coordinates } from './coordinates.model';
import { CoordinatesPopupService } from './coordinates-popup.service';
import { CoordinatesService } from './coordinates.service';
import { Location, LocationService } from '../location';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-coordinates-dialog',
    templateUrl: './coordinates-dialog.component.html'
})
export class CoordinatesDialogComponent implements OnInit {

    coordinates: Coordinates;
    isSaving: boolean;

    locations: Location[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private coordinatesService: CoordinatesService,
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
        if (this.coordinates.id !== undefined) {
            this.subscribeToSaveResponse(
                this.coordinatesService.update(this.coordinates));
        } else {
            this.subscribeToSaveResponse(
                this.coordinatesService.create(this.coordinates));
        }
    }

    private subscribeToSaveResponse(result: Observable<Coordinates>) {
        result.subscribe((res: Coordinates) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Coordinates) {
        this.eventManager.broadcast({ name: 'coordinatesListModification', content: 'OK'});
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
    selector: 'jhi-coordinates-popup',
    template: ''
})
export class CoordinatesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coordinatesPopupService: CoordinatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.coordinatesPopupService
                    .open(CoordinatesDialogComponent as Component, params['id']);
            } else {
                this.coordinatesPopupService
                    .open(CoordinatesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
