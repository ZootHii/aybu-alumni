package com.aybu9.aybualumni.event.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityEventDto {

    @NotBlank
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull
    private Boolean isOnline;

    // online ise önde şehir göstermeyelim değişecek
    private Integer cityId;

    @NotBlank
    @NotNull // online ise online url konacak
    @Size(max = 510)
    private String address;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @FutureOrPresent
    private Date startDateTime;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Future
    private Date endDateTime;

    private String visibility;

    private Collection<Long> eventSpeakerUsersIds;
}
