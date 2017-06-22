import { BaseEntity } from './../../shared';

export class JobNotification implements BaseEntity {
    constructor(
        public id?: number,
        public headline?: string,
        public notificationDate?: any,
        public organization?: string,
        public jobRole?: string,
        public jobLocation?: string,
        public totalVacancyCount?: number,
        public additionalQualification?: any,
        public workExperience?: number,
        public salary?: number,
        public applicationDeadline?: any,
        public description?: any,
        public notificationLink?: any,
        public applicationLink?: any,
        public examLocation?: string,
        public clientTypeId?: number,
        public jobSectorId?: number,
        public jobTypeId?: number,
        public educationId?: number,
        public testSkills?: BaseEntity[],
        public selectionProcedures?: BaseEntity[],
        public quotaJobDetails?: BaseEntity[],
        public writtenExamLanguages?: BaseEntity[],
        public languageProficiencies?: BaseEntity[],
    ) {
    }
}
