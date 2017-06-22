import { BaseEntity } from './../../shared';

export class QuotaJobDetails implements BaseEntity {
    constructor(
        public id?: number,
        public vacancyCount?: number,
        public bornBefore?: any,
        public bornAfter?: any,
        public quotaCategoryId?: number,
        public jobNotificationId?: number,
    ) {
    }
}
