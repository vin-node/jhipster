import { BaseEntity } from './../../shared';

export class Coordinates implements BaseEntity {
    constructor(
        public id?: number,
        public locationId?: number,
    ) {
    }
}
