package com.aybu9.aybualumni.user.mappers;

import com.aybu9.aybualumni.user.models.UserContactInfo;
import com.aybu9.aybualumni.user.models.dtos.UserContactInfoDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserContactInfoMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserContactInfoFromUserContactInfoDto(UserContactInfoDto userContactInfoDto, @MappingTarget UserContactInfo userContactInfo);
}

// https://www.baeldung.com/spring-data-partial-update#1-mapping-strategy
// https://www.baeldung.com/mapstruct