package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.domain.Course;
import it.tacticalpeople.tpdsserver.dto.CourseDto;
import it.tacticalpeople.tpdsserver.repository.CourseRepository;
import it.tacticalpeople.tpdsserver.service.CourseService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import it.tacticalpeople.tpdsserver.webutils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/courses")
public class CourseController {

    private final Logger log = LoggerFactory.getLogger(CourseController.class);

    private static final String ENTITY_NAME = "Course";

    //	@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCourse(@RequestBody CourseDto protDTO) throws Exception
    {
        log.debug("REST request to save Course : {}", protDTO);
        return ResponseEntity.ok(courseService.save(protDTO));
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity partialUpdateCourse(
            @PathVariable(value = "id") final Long id,
            @RequestBody CourseDto protDTO) {
        return ResponseEntity.ok(courseService.partialUpdate(id, protDTO));
    }

    @GetMapping("/")
    public List<CourseDto> getAllCourses() {
        log.debug("REST request to get all Courses");
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        Optional<CourseDto> protDTO = Optional.ofNullable(courseService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
