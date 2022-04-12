package com.aybu9.aybualumni.page.models;

import com.aybu9.aybualumni.core.models.IntegerBaseModel;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turkey_cities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class City extends IntegerBaseModel {

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String name;
}
