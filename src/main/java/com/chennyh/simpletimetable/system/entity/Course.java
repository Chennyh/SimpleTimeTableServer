package com.chennyh.simpletimetable.system.entity;

import com.chennyh.simpletimetable.system.web.representation.CourseRepresentation;
import com.chennyh.simpletimetable.system.web.request.CourseAddRequest;
import com.chennyh.simpletimetable.system.web.request.CourseUpdateRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public CourseRepresentation toCourseRepresentation() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        ArrayList<Integer> weekList = gson.fromJson(this.getWeekList(), listType);

        return CourseRepresentation.builder()
                .id(this.getId())
                .name(this.getName())
                .time(this.getTime())
                .room(this.getRoom())
                .teacher(this.getTeacher())
                .weekList(weekList)
                .start(this.getStart())
                .step(this.getStep())
                .day(this.getDay())
                .term(this.getTerm())
                .color(this.getColor())
                .build();
    }

    public void updateForm(CourseUpdateRequest courseUpdateRequest) {
        if (Objects.nonNull(courseUpdateRequest.getName())) {
            this.setName(courseUpdateRequest.getName());
        }
        if (Objects.nonNull(courseUpdateRequest.getTime())) {
            this.setTime(courseUpdateRequest.getTime());
        }
        if (Objects.nonNull(courseUpdateRequest.getRoom())) {
            this.setRoom(courseUpdateRequest.getRoom());
        }
        if (Objects.nonNull(courseUpdateRequest.getTeacher())) {
            this.setTeacher(courseUpdateRequest.getTeacher());
        }
        if (Objects.nonNull(courseUpdateRequest.getWeekList())) {
            this.setWeekList(courseUpdateRequest.getWeekList().toString());
        }
        if (Objects.nonNull(courseUpdateRequest.getStart())) {
            this.setStart(courseUpdateRequest.getStart());
        }
        if (Objects.nonNull(courseUpdateRequest.getStep())) {
            this.setStep(courseUpdateRequest.getStep());
        }
        if (Objects.nonNull(courseUpdateRequest.getDay())) {
            this.setDay(courseUpdateRequest.getDay());
        }
        if (Objects.nonNull(courseUpdateRequest.getTerm())) {
            this.setTerm(courseUpdateRequest.getTerm());
        }
        if (Objects.nonNull(courseUpdateRequest.getColor())) {
            this.setColor(courseUpdateRequest.getColor());
        }
    }
}
