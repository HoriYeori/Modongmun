package com.ssafy.modongmun.school.schedule;

import com.ssafy.modongmun.config.auth.LoginUser;
import com.ssafy.modongmun.config.auth.UserPrincipal;
import com.ssafy.modongmun.school.schedule.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule/schedules")
    public ResponseEntity<ScheduleDto> registerSchedule(@RequestBody ScheduleDto scheduleRegisterDto, @LoginUser UserPrincipal loginUser) {
        ScheduleDto savedSchedule = scheduleService.registerSchedule(scheduleRegisterDto, loginUser);
        return new ResponseEntity<ScheduleDto>(savedSchedule, HttpStatus.OK);
    }

    @GetMapping("/schedule/schedules/{schedule_id}")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable("schedule_id") Long scheduleId) {
        ScheduleDto scheduleDto = scheduleService.getSchedule(scheduleId);
        return new ResponseEntity<ScheduleDto>(scheduleDto, HttpStatus.OK);
    }

    @GetMapping("/schedule/schedules")
    public ResponseEntity<List<ScheduleDto>> getScheduleList() {
        List<ScheduleDto> scheduleDtoList = scheduleService.getScheduleList();
        return new ResponseEntity<List<ScheduleDto>>(scheduleDtoList, HttpStatus.OK);
    }

    @PatchMapping("/schedule/schedules/{schedule_id}")
    public ResponseEntity<ScheduleDto> modifySchedule(@PathVariable("schedule_id") Long scheduleId,
                                                      @RequestBody ScheduleDto scheduleDto,
                                                      @LoginUser UserPrincipal loginUser){
        ScheduleDto modifiedSchedule = scheduleService.modifySchedule(scheduleId, scheduleDto);
        return new ResponseEntity<ScheduleDto>(modifiedSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/schedule/schedules/{schedule_id}")
    public ResponseEntity<ScheduleDto> deleteSchedule(@PathVariable("schedule_id") Long scheduleId) {
        ScheduleDto deletedSchedule = scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<ScheduleDto>(deletedSchedule, HttpStatus.OK);
    }

}
