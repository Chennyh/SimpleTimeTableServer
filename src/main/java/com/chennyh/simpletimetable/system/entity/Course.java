package com.chennyh.simpletimetable.system.entity;

import com.chennyh.simpletimetable.system.web.request.CourseAddRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * @author Chennyh
 * @date 2020/6/12 9:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course extends AbstractAuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    /**
     * 课程名
     */
    private String name;

    /**
     * 上课时间
     */
    private String time;

    /**
     * 教室
     */
    private String room;

    /**
     * 教师
     */
    private String teacher;

    /**
     * 第几周至第几周上
     */
    private String weekList;

    /**
     * 开始上课的节次
     */
    @Column(columnDefinition = "integer default -1")
    private int start;

    /**
     * 上课节数
     */
    @Column(columnDefinition = "integer default -1")
    private int step;

    /**
     * 周几上
     */
    @Column(columnDefinition = "integer default -1")
    private int day;

    /**
     * 学期
     */
    private String term;

    /**
     * 一个随机数，用于对应课程的颜色
     */
    @Column(columnDefinition = "integer default -1")
    private int color;

    public static Course of(CourseAddRequest courseAddRequest) {
        if (courseAddRequest.getWeekList() == null) {
            ArrayList<Integer> weekList = new ArrayList<>();
            weekList.add(1);
            weekList.add(20);
            courseAddRequest.setWeekList(weekList);
        }

        return Course.builder()
                .name(courseAddRequest.getName())
                .time(courseAddRequest.getTime())
                .room(courseAddRequest.getRoom())
                .teacher(courseAddRequest.getTeacher())
                .weekList(courseAddRequest.getWeekList().toString())
                .start(courseAddRequest.getStart())
                .step(courseAddRequest.getStep())
                .day(courseAddRequest.getDay())
                .term(courseAddRequest.getTerm())
                .color(courseAddRequest.getColor())
                .build();

    }
}
