import { BaseEntity } from './../../shared';

export class JobType implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public jobNotifications?: BaseEntity[],
    ) {
    }
}
