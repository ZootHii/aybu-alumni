package com.aybu9.aybualumni.page.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_pages", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "page_id"
        })
})
public class CompanyPage {
    
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "page_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Page page;

    @OneToOne
    @JoinColumn(name = "company_sector_id", referencedColumnName = "id", nullable = false)
    private CompanySector companySector;

    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;
    
    public CompanyPage(Page page, CompanySector companySector, City city) {
        this.page = page;
        this.companySector = companySector;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CompanyPage that = (CompanyPage) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}