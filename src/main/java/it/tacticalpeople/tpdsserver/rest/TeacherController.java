package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.domain.Teacher;
import it.tacticalpeople.tpdsserver.dto.TeacherDto;
import it.tacticalpeople.tpdsserver.repository.TeacherRepository;
import it.tacticalpeople.tpdsserver.service.TeacherService;
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
@RequestMapping("/teachers")
public class TeacherController {

    private final Logger log = LoggerFactory.getLogger(TeacherController.class);

    private static final String ENTITY_NAME = "Teacher";

    //	@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    //todo
    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity save(@RequestBody TeacherDto protDTO) throws Exception
    {
        log.debug("REST request to save Teacher : {}", protDTO);
        return ResponseEntity.ok(teacherService.save(protDTO));
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity partialUpdateTeacher(
            @PathVariable(value = "id") final Long id,
            @RequestBody TeacherDto protDTO) {
        log.debug("TeacherController - /patchTeacher - partialUpdateTeacher");
        return ResponseEntity.ok(teacherService.partialUpdate(id, protDTO));
    }

    @GetMapping("/")
    public List<TeacherDto> getAllTeachers() {
        log.debug("REST request to get all Teachers");
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        Optional<TeacherDto> protDTO = Optional.ofNullable(teacherService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProt(@PathVariable Long id) {
        log.debug("REST request to delete Prot : {}", id);
        teacherService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }


}
