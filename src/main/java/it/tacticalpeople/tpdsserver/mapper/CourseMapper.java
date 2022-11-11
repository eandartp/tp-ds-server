package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Course;
import it.tacticalpeople.tpdsserver.dto.CourseDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper implements Serializable{

    private static final long serialVersionUID = 1L;

    //todo Sistemare relazioni e DTO
    public static CourseDto fromCourseToDto(Course course)
    {
        CourseDto courseDto = new CourseDto();
        courseDto.setName(course.getName());
        courseDto.setDateStart(course.getDateStart());
        courseDto.setDateEnd(course.getDateEnd());
        courseDto.setTeachers(TeacherMapper.fromTeacherToDto(course.getTeacher()));
        //course.setTeacher(TeacherMapper.fromDtoToTeacher((TeacherDto) courseDto.getTeachers()));
        return courseDto;
    }

    public static List<CourseDto> fromListCourseToDtoList(List<Course> courses){
        return courses.stream().map(CourseMapper::fromCourseToDto)
                .collect(Collectors.toList());
    }

    public static Course fromDtoToCourse(CourseDto courseDto)
    {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDateStart(courseDto.getDateStart());
        course.setDateEnd(courseDto.getDateEnd());
        return course;
    }

    public static List<Course> fromListDtoToSkillList(List<CourseDto> courseDtos){
        return courseDtos.stream().map(CourseMapper::fromDtoToCourse)
                .collect(Collectors.toList());
    }

}
