package com.aybu9.aybualumni.user.mappers;

import com.aybu9.aybualumni.user.One;
import com.aybu9.aybualumni.user.OneDto;
import com.aybu9.aybualumni.user.models.UserContactInfo;
import com.aybu9.aybualumni.user.models.dtos.UserContactInfoDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserContactInfoMapper {

    UserContactInfoMapper INSTANCE = Mappers.getMapper(UserContactInfoMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserContactInfoFromUserContactInfoDto(UserContactInfoDto userContactInfoDto,
                                                     @MappingTarget UserContactInfo userContactInfo);

    @Mapping(target="id", source="one.id")
    OneDto createOne (One one);
}
