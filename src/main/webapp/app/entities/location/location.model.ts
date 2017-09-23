import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public coordinatesAsString?: string,
    ) {
    }
}
