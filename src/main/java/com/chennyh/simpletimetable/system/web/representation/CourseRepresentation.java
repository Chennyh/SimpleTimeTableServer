package com.chennyh.simpletimetable.system.web.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Chennyh
 * @date 2020/6/13 10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRepresentation {
    private Long id;
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
