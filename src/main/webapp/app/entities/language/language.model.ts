import { BaseEntity } from './../../shared';

export class Language implements BaseEntity {
    constructor(
        public id?: number,
        public language?: string,
        public jobNotificationWrittenExamLanguages?: BaseEntity[],
        public jobNotificationLanguageProficiencies?: BaseEntity[],
    ) {
    }
}
