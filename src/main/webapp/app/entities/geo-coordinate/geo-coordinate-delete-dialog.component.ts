import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GeoCoordinate } from './geo-coordinate.model';
import { GeoCoordinatePopupService } from './geo-coordinate-popup.service';
import { GeoCoordinateService } from './geo-coordinate.service';

@Component({
    selector: 'jhi-geo-coordinate-delete-dialog',
    templateUrl: './geo-coordinate-delete-dialog.component.html'
})
export class GeoCoordinateDeleteDialogComponent {

    geoCoordinate: GeoCoordinate;

    constructor(
        private geoCoordinateService: GeoCoordinateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.geoCoordinateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'geoCoordinateListModification',
                content: 'Deleted an geoCoordinate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-geo-coordinate-delete-popup',
    template: ''
})
export class GeoCoordinateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private geoCoordinatePopupService: GeoCoordinatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.geoCoordinatePopupService
                .open(GeoCoordinateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
