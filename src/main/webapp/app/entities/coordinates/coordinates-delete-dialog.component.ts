import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Coordinates } from './coordinates.model';
import { CoordinatesPopupService } from './coordinates-popup.service';
import { CoordinatesService } from './coordinates.service';

@Component({
    selector: 'jhi-coordinates-delete-dialog',
    templateUrl: './coordinates-delete-dialog.component.html'
})
export class CoordinatesDeleteDialogComponent {

    coordinates: Coordinates;

    constructor(
        private coordinatesService: CoordinatesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.coordinatesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'coordinatesListModification',
                content: 'Deleted an coordinates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-coordinates-delete-popup',
    template: ''
})
export class CoordinatesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coordinatesPopupService: CoordinatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.coordinatesPopupService
                .open(CoordinatesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
