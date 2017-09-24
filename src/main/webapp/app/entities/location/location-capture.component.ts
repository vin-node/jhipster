import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
@Component({
    selector: 'jhi-location-capture',
    template: "<button type='button' class='btn btn-secondary' data-dismiss='modal' (click)='startEditing()'><span class='fa fa-ban' > </span>&nbsp;<span>Start Editing</span></button><button type='button' class='btn btn-secondary' data-dismiss='modal'(click)='stopEditing()'><span class='fa fa-ban'></span>&nbsp;<span>Stop Editing</span></button>"
})
export class LocationCaptureComponent implements OnInit {
    serviceId: number;
    @Output() notify: EventEmitter<string> = new EventEmitter<string>();
    ngOnInit() {

    }

    startEditing() {
        if (this.serviceId != null) {
            return;
        }
        if (navigator.geolocation) {
            let options = {
                enableHighAccuracy: true,
                timeout: 100000,
                maximumAge: 0
            };
            this.serviceId = navigator.geolocation.watchPosition((position) => {
                this.notify.emit("~lat:" + position.coords.latitude + "^long:" + position.coords.longitude);
                console.log("position:" + "~lat:" + position.coords.latitude + "^long:" + position.coords.longitude);
              }, this.onError, options);
        } else {
            console.log("Geo location not supported");
        }
    }

    stopEditing() {
        navigator.geolocation.clearWatch(this.serviceId);
        this.serviceId = null;
    }

    informLocationChange(position) {
        this.notify.emit("~lat:" + position.coords.latitude + "^long:" + position.coords.longitude);
        console.log("position:" + "~lat:" + position.coords.latitude + "^long:" + position.coords.longitude);
    }

    private onError(error: any) {
        console.log("error.message" + error.message);
    }
}
