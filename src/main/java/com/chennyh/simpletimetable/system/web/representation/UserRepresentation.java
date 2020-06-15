package com.chennyh.simpletimetable.system.web.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRepresentation {
    private Long id;
    private String username;
    private String email;
}
