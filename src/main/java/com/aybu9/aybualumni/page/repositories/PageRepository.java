package com.aybu9.aybualumni.page.repositories;

import com.aybu9.aybualumni.page.models.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    Page getByPageUrl(String pageUrl);

    Boolean existsByPageUrl(String pageUrl);
}
