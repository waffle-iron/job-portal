import { BaseEntity } from './../../shared';

export class Education implements BaseEntity {
    constructor(
        public id?: number,
        public education?: string,
        public jobNotifications?: BaseEntity[],
    ) {
    }
}
