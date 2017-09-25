import { BaseEntity } from './../../shared';

export class GeoCoordinate implements BaseEntity {
    constructor(
        public id?: number,
        public lat?: number,
        public lon?: number,
        public location?: BaseEntity,
    ) {
    }
}
