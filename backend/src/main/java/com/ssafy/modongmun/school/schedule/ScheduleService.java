package com.ssafy.modongmun.school.schedule;

import com.ssafy.modongmun.config.auth.UserPrincipal;
import com.ssafy.modongmun.school.School;
import com.ssafy.modongmun.school.SchoolRepository;
import com.ssafy.modongmun.school.schedule.dto.ScheduleDto;
import com.ssafy.modongmun.user.User;
import com.ssafy.modongmun.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    /*** Repository ***/
    private final ScheduleRepository scheduleRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public ScheduleDto registerSchedule(ScheduleDto scheduleRegisterDto, UserPrincipal loginUser) {
        School school = schoolRepository.findById(scheduleRegisterDto.getSchoolId())
                .orElseThrow(() -> new IllegalArgumentException("Illegal school ID !"));
        User user = userRepository.findById(Long.parseLong(loginUser.getName()))
                .orElseThrow(() -> new RuntimeException("Invalid authenticated user"));

        Schedule schedule = Schedule.builder()
                .school(school)
                .user(user)
                .title(scheduleRegisterDto.getTitle())
                .location(scheduleRegisterDto.getLocation())
                .content(scheduleRegisterDto.getContent())
                .startDate(scheduleRegisterDto.getStartDate())
                .endDate(scheduleRegisterDto.getEndDate())
                .createDate(LocalDateTime.now())
                .build();
        scheduleRepository.save(schedule);

        return ScheduleDto.toDto(schedule);
    }

    public ScheduleDto getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Illegal schedules id!"));

        return ScheduleDto.toDto(schedule);
    }

    public List<ScheduleDto> getScheduleList() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();

        for(Schedule schedule : scheduleList){
            ScheduleDto scheduleDto = ScheduleDto.toDto(schedule);
            scheduleDtoList.add(scheduleDto);
        }

        return scheduleDtoList;
    }

    @Transactional(rollbackFor = Exception.class)
    public ScheduleDto modifySchedule(Long scheduleId, ScheduleDto scheduleDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Illegal schedules id!"));
        schedule.update(scheduleDto);

        return ScheduleDto.toDto(schedule);
    }

    @Transactional(rollbackFor = Exception.class)
    public ScheduleDto deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Illegal schedules id!"));
        scheduleRepository.delete(schedule);

        return ScheduleDto.toDto(schedule);
    }

}