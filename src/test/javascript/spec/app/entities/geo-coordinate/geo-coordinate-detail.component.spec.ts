/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GeoCoordinateDetailComponent } from '../../../../../../main/webapp/app/entities/geo-coordinate/geo-coordinate-detail.component';
import { GeoCoordinateService } from '../../../../../../main/webapp/app/entities/geo-coordinate/geo-coordinate.service';
import { GeoCoordinate } from '../../../../../../main/webapp/app/entities/geo-coordinate/geo-coordinate.model';

describe('Component Tests', () => {

    describe('GeoCoordinate Management Detail Component', () => {
        let comp: GeoCoordinateDetailComponent;
        let fixture: ComponentFixture<GeoCoordinateDetailComponent>;
        let service: GeoCoordinateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [GeoCoordinateDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GeoCoordinateService,
                    JhiEventManager
                ]
            }).overrideTemplate(GeoCoordinateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GeoCoordinateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeoCoordinateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GeoCoordinate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.geoCoordinate).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
