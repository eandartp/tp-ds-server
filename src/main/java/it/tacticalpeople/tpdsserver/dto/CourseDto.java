package it.tacticalpeople.tpdsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Instant dateStart;
    private Instant dateEnd;

    private TeacherDto teachers;
}
