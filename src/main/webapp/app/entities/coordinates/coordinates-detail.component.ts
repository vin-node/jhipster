import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Coordinates } from './coordinates.model';
import { CoordinatesService } from './coordinates.service';

@Component({
    selector: 'jhi-coordinates-detail',
    templateUrl: './coordinates-detail.component.html'
})
export class CoordinatesDetailComponent implements OnInit, OnDestroy {

    coordinates: Coordinates;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private coordinatesService: CoordinatesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCoordinates();
    }

    load(id) {
        this.coordinatesService.find(id).subscribe((coordinates) => {
            this.coordinates = coordinates;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCoordinates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'coordinatesListModification',
            (response) => this.load(this.coordinates.id)
        );
    }
}
