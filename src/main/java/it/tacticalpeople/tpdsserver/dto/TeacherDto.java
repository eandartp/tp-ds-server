package it.tacticalpeople.tpdsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String matricola;
    private String name;

    private List<CourseDto> courses;

}
