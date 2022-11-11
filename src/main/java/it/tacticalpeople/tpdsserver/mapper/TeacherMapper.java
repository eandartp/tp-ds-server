package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Teacher;
import it.tacticalpeople.tpdsserver.dto.TeacherDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public static TeacherDto fromTeacherToDto(Teacher teacher)
    {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setName(teacher.getName());
        teacherDto.setMatricola(teacher.getMatricola());
        return teacherDto;
    }

    public static List<TeacherDto> fromListTeacherToDtoList(List<Teacher> teachers){
        return teachers.stream().map(TeacherMapper::fromTeacherToDto)
                .collect(Collectors.toList());
    }

    public static Teacher fromTeacherToDto(TeacherDto teacherDto)
    {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDto.getName());
        teacher.setMatricola(teacherDto.getMatricola());
        return teacher;
    }

}
