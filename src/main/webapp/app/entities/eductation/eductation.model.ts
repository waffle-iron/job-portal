import { BaseEntity } from './../../shared';

export class Eductation implements BaseEntity {
    constructor(
        public id?: number,
        public education?: string,
    ) {
    }
}
