import { BaseEntity } from './../../shared';

export class TestSkill implements BaseEntity {
    constructor(
        public id?: number,
        public skill?: string,
        public jobNotifications?: BaseEntity[],
    ) {
    }
}
