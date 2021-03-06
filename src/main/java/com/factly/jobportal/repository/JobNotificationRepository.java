package com.factly.jobportal.repository;

import com.factly.jobportal.domain.JobNotification;
import com.factly.jobportal.web.domain.JobsCount;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the JobNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobNotificationRepository extends JpaRepository<JobNotification,Long> {

    @Query("select distinct job_notification from JobNotification job_notification left join fetch job_notification.testSkills " +
        "left join fetch job_notification.selectionProcedures left join fetch job_notification.writtenExamLanguages " +
        "left join fetch job_notification.languageProficiencies")
    List<JobNotification> findAllWithEagerRelationships();

    @Query("select job_notification from JobNotification job_notification left join fetch job_notification.testSkills " +
        "left join fetch job_notification.selectionProcedures left join fetch job_notification.writtenExamLanguages " +
        "left join fetch job_notification.languageProficiencies where job_notification.id =:id")
    JobNotification findOneWithEagerRelationships(@Param("id") Long id);

    @Query("SELECT NEW com.factly.jobportal.web.domain.JobsCount(sum(jn.totalVacancyCount)) " +
        "FROM JobNotification jn JOIN jn.clientType ct " +
        "WHERE ct.type= :clientType")
    public JobsCount retrieveJobsCount(@Param("clientType") String clientType);

    @Query("SELECT NEW com.factly.jobportal.web.domain.JobsCount(sum(jn.totalVacancyCount)) " +
        "FROM JobNotification jn JOIN jn.jobSector js " +
        "WHERE js.sector= :sector")
    public JobsCount retrieveSectorJobsCount(@Param("sector") String sector);
}
