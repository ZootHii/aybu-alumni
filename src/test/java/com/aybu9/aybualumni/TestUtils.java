//package com.aybu9.aybualumni;
//
//import com.aybu9.aybualumni.user.models.User;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//public class TestUtils {
//
//    public static List<User> generateUsers() {
//        return IntStream.range(0, 5).mapToObj(value -> User.builder()
//                .id((long) value)
//                .email(value + "@test.com")
//                .password(value + "password")
//                .name(value + "name")
//                .surname(value + "surname")
//                .nameInCollege(value + "nameInCollege")
//                .surnameInCollege(value + "surnameInCollege")
//                .department(value + "department")
//                .grade(value + "grade")
//                .about(value + "about")
//                .profileUrl(value + "profileUrl")
//                .profileUrl(value + "profileUrl")
//                .profilePhotoUrl(value + "profilePhotoUrl")
//                .coverPhotoUrl(value + "coverPhotoUrl")
//                .build()).collect(Collectors.toList());
//    }
//}
