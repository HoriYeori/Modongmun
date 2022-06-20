package com.ssafy.modongmun.user;

import com.ssafy.modongmun.config.auth.UserPrincipal;
import com.ssafy.modongmun.school.School;
import com.ssafy.modongmun.school.SchoolRepository;
import com.ssafy.modongmun.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

//    public UserDto signup(SignupDto signupDto) {
//        School elementarySchool = schoolRepository.findById(signupDto.getElementarySchoolId()).orElse(null);
//        School middleSchool = schoolRepository.findById(signupDto.getMiddleSchoolId()).orElse(null);
//        School highSchool = schoolRepository.findById(signupDto.getHighSchoolId()).orElse(null);
//
//        User savedUser = userRepository.save(User.builder()
//                .userNumber(signupDto.getUserNumber())
//                .username(signupDto.getUsername())
//                .elementarySchool(elementarySchool)
//                .egYear(signupDto.getEgYear())
//                .middleSchool(middleSchool)
//                .mgYear(signupDto.getMgYear())
//                .highSchool(highSchool)
//                .hgYear(signupDto.getHgYear())
//                .registerDate(LocalDateTime.now())
//                .build());
//
//        return UserDto.toDto(savedUser);
//    }

    public UserDto getUser(String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new IllegalArgumentException("Illegal user email!"));
        return UserDto.toDto(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDto modifyUser(Long userId, UserDto userDto, UserPrincipal loginUser) {
        if (Long.parseLong(loginUser.getName()) != userId)
            return null;

        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Illegal user id!"));

        School elementarySchool = schoolRepository.findById(userDto.getElementarySchoolId())
                .orElseThrow(() -> new IllegalArgumentException("Illegal elementary school!"));
        School middleSchool = schoolRepository.findById(userDto.getMiddleSchoolId())
                .orElseThrow(() -> new IllegalArgumentException("Illegal middle school !"));
        School highSchool = schoolRepository.findById(userDto.getHighSchoolId())
                .orElseThrow(() -> new IllegalArgumentException("Illegal high school !"));
        System.out.println(userDto.getBirthYear());
        userDto = UserDto.builder()
                .elementarySchool(elementarySchool)
                .egYear(userDto.getEgYear())
                .middleSchool(middleSchool)
                .mgYear(userDto.getMgYear())
                .highSchool(highSchool)
                .hgYear(userDto.getHgYear())
                .birthYear(userDto.getBirthYear())
                .build();
        user.update(userDto);

        return UserDto.toDto(user);
    }
}
