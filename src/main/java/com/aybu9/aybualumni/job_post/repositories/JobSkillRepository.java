package com.aybu9.aybualumni.job_post.repositories;

import com.aybu9.aybualumni.job_post.models.job.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, Integer> {

    Set<JobSkill> getAllByIdIn(Collection<Integer> ids);
}
