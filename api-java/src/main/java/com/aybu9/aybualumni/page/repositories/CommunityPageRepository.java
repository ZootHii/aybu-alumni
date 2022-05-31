package com.aybu9.aybualumni.page.repositories;

import com.aybu9.aybualumni.page.models.CommunityPage;
import com.aybu9.aybualumni.page.models.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CommunityPageRepository extends JpaRepository<CommunityPage, Long> {
    CommunityPage getByPage(Page page);

    @Query("select cp from CommunityPage cp where (lower(cp.page.name) like lower(concat('%', concat(:name, '%'))) or " +
            "lower(cp.page.name) like lower(concat('', concat(:name, '%'))))")
    org.springframework.data.domain.Page<CommunityPage> searchByNameContainsOrStartsWith(@Param("name") String name,
                                                                                         Pageable pageable);
}
