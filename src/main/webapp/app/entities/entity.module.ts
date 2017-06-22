import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JobportalTestSkillModule } from './test-skill/test-skill.module';
import { JobportalJobTypeModule } from './job-type/job-type.module';
import { JobportalJobSectorModule } from './job-sector/job-sector.module';
import { JobportalEductationModule } from './eductation/eductation.module';
import { JobportalClientTypeModule } from './client-type/client-type.module';
import { JobportalQuotaCategoryModule } from './quota-category/quota-category.module';
import { JobportalSelectionProcedureModule } from './selection-procedure/selection-procedure.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JobportalTestSkillModule,
        JobportalJobTypeModule,
        JobportalJobSectorModule,
        JobportalEductationModule,
        JobportalClientTypeModule,
        JobportalQuotaCategoryModule,
        JobportalSelectionProcedureModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JobportalEntityModule {}
