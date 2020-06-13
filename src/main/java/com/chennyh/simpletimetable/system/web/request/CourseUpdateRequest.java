package com.chennyh.simpletimetable.system.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Chennyh
 * @date 2020/6/13 16:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {
    private String name;
    private String time;
    private String room;
    private String teacher;
    private List<Integer> weekList;
    private int start;
    private int step;
    private int day;
    private String term;
    private int color;
}
