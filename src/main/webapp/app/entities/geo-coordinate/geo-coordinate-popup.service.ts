import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GeoCoordinate } from './geo-coordinate.model';
import { GeoCoordinateService } from './geo-coordinate.service';

@Injectable()
export class GeoCoordinatePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private geoCoordinateService: GeoCoordinateService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.geoCoordinateService.find(id).subscribe((geoCoordinate) => {
                    this.ngbModalRef = this.geoCoordinateModalRef(component, geoCoordinate);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.geoCoordinateModalRef(component, new GeoCoordinate());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    geoCoordinateModalRef(component: Component, geoCoordinate: GeoCoordinate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.geoCoordinate = geoCoordinate;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
