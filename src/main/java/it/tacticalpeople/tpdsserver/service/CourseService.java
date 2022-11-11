package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Course;
import it.tacticalpeople.tpdsserver.domain.Teacher;
import it.tacticalpeople.tpdsserver.dto.CourseDto;
import it.tacticalpeople.tpdsserver.mapper.CourseMapper;
import it.tacticalpeople.tpdsserver.repository.CourseRepository;
import it.tacticalpeople.tpdsserver.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseMapper mapper;

    public CourseDto save(CourseDto courseDto) throws ResourceException {
        log.debug("Request to save Course : {}", courseDto);
        Course course = CourseMapper.fromDtoToCourse(courseDto);
        course = courseRepository.save(course);
        Teacher teacher = teacherRepository.findByMatricola(courseDto.getTeachers().getMatricola())
                .orElseThrow(() -> new ResourceException("Matricola code is alredy used"));
        course.setTeacher(teacher);
        return CourseMapper.fromCourseToDto(course);
    }

    public CourseDto partialUpdate(Long id, CourseDto courseDto) {
        log.debug("Course - partialUpdate - Service");
        Course course = null;
        try {
            course = courseRepository.findById(id).orElseThrow(() -> new ResourceException("Invalid Id"));
        } catch (ResourceException e) {
            throw new RuntimeException(e);
        }
        course = CourseMapper.fromDtoToCourse(courseDto);
        course = courseRepository.save(course);
        return CourseMapper.fromCourseToDto(course);

    }

    @Transactional(readOnly = true)
    public List<CourseDto> findAll() {
        log.debug("Request to get all Course");
        return courseRepository.findAll().stream()
                .map(course -> mapper.fromCourseToDto(course))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public CourseDto findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        Optional<Course> course = courseRepository.findById(id);
        CourseDto courseDto = null;
        if (course.isPresent()) {
            courseDto = CourseMapper.fromCourseToDto(course.get());
        }
        return courseDto;
    }

    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }

}
