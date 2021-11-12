package com.aybu9.aybualumni.page.repositories;

import com.aybu9.aybualumni.page.models.CompanyPage;
import com.aybu9.aybualumni.page.models.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyPageRepository extends JpaRepository<CompanyPage, Long> {
    CompanyPage getByPage(Page page);
}
