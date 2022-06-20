package com.ssafy.modongmun.school;

import com.ssafy.modongmun.school.dto.SchoolDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping("/school/schools")
    public ResponseEntity<?> register(@RequestBody SchoolDto schoolDto) {
        System.out.println("Got request :: " + schoolDto);
        schoolService.register(schoolDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/school/schools")
    public ResponseEntity<List<SchoolDto>> searchSchool(@RequestParam("keyword") String keyword) {
        List<SchoolDto> schoolDtoList = schoolService.searchSchool(keyword);
        return new ResponseEntity<>(schoolDtoList, HttpStatus.OK);
    }

}
