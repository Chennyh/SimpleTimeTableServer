package com.chennyh.simpletimetable.system.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Chennyh
 * @date 2020/6/12 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseAddRequest {

    @NotNull
    private Long userId;

    @NotBlank
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
