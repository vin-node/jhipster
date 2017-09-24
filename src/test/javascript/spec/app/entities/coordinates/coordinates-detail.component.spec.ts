/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CoordinatesDetailComponent } from '../../../../../../main/webapp/app/entities/coordinates/coordinates-detail.component';
import { CoordinatesService } from '../../../../../../main/webapp/app/entities/coordinates/coordinates.service';
import { Coordinates } from '../../../../../../main/webapp/app/entities/coordinates/coordinates.model';

describe('Component Tests', () => {

    describe('Coordinates Management Detail Component', () => {
        let comp: CoordinatesDetailComponent;
        let fixture: ComponentFixture<CoordinatesDetailComponent>;
        let service: CoordinatesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [CoordinatesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CoordinatesService,
                    JhiEventManager
                ]
            }).overrideTemplate(CoordinatesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoordinatesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoordinatesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Coordinates(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.coordinates).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
