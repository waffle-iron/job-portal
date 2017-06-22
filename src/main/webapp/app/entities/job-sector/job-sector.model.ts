import { BaseEntity } from './../../shared';

export class JobSector implements BaseEntity {
    constructor(
        public id?: number,
        public sector?: string,
        public iconUrl?: string,
        public jobNotifications?: BaseEntity[],
    ) {
    }
}
