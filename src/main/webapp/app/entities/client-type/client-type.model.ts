import { BaseEntity } from './../../shared';

export class ClientType implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
    ) {
    }
}
