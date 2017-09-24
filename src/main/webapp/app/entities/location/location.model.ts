import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public coordinatesAsString?: string,
        public coordinates?: string,
        public coords?: BaseEntity[],
        public bs?: BaseEntity[],
        public cs?: BaseEntity[],
    ) {
    }
}
