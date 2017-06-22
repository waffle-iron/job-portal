import { BaseEntity } from './../../shared';

export class QuotaCategory implements BaseEntity {
    constructor(
        public id?: number,
        public category?: string,
    ) {
    }
}
