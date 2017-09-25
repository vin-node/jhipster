import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GeoCoordinate } from './geo-coordinate.model';
import { GeoCoordinateService } from './geo-coordinate.service';

@Component({
    selector: 'jhi-geo-coordinate-detail',
    templateUrl: './geo-coordinate-detail.component.html'
})
export class GeoCoordinateDetailComponent implements OnInit, OnDestroy {

    geoCoordinate: GeoCoordinate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private geoCoordinateService: GeoCoordinateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGeoCoordinates();
    }

    load(id) {
        this.geoCoordinateService.find(id).subscribe((geoCoordinate) => {
            this.geoCoordinate = geoCoordinate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGeoCoordinates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'geoCoordinateListModification',
            (response) => this.load(this.geoCoordinate.id)
        );
    }
}
