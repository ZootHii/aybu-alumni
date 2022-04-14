package com.aybu9.aybualumni.fake_obs.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fake_obs_datas")
public class FakeOBSData {

    @Id
    @Column(updatable = false)
    @NotBlank
    @NotNull
    @Size(min = 11, max = 11)
    private String tcIdentityNumber;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String surname;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    //@Size(min = 1, max = 1)
    private String grade; // 3. ve 4. ve ALUMNI

    @Column(nullable = false)
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String department;
}
