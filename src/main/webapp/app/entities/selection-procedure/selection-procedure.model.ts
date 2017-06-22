import { BaseEntity } from './../../shared';

export class SelectionProcedure implements BaseEntity {
    constructor(
        public id?: number,
        public procedure?: string,
    ) {
    }
}
