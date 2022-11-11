package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Teacher;
import it.tacticalpeople.tpdsserver.dto.TeacherDto;
import it.tacticalpeople.tpdsserver.mapper.TeacherMapper;
import it.tacticalpeople.tpdsserver.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private TeacherRepository teacherRepository;


    @Autowired
    private TeacherMapper mapper;

    public TeacherDto save(TeacherDto teacherDto) {
        log.debug("Request to save Teacher : {}", teacherDto);
        List<Teacher> td1 = teacherRepository.findDistinctByMatricolaAndName(teacherDto.getName(), teacherDto.getMatricola());
        if (!td1.isEmpty()){
            return mapper.fromTeacherToDto(td1.get(0));
        }
        Teacher teacher = mapper.fromTeacherToDto(teacherDto);

        teacher = teacherRepository.save(teacher);
        return mapper.fromTeacherToDto(teacher);
    }

    public TeacherDto partialUpdate(Long id, TeacherDto teacherDto) {
        log.debug("TeacherService - partialUpdate");
        Teacher teacher = null;
        try {
            teacher = teacherRepository.findById(id).orElseThrow(() -> new ResourceException("Invalid Id"));
        } catch (ResourceException e) {
            throw new RuntimeException(e);
        }
        teacher = TeacherMapper.fromTeacherToDto(teacherDto);
        teacher = teacherRepository.save(teacher);
        return mapper.fromTeacherToDto(teacher);

    }


    @Transactional(readOnly = true)
    public List<TeacherDto> findAll() {
        log.debug("Request to get all Teacher");
        return mapper.fromListTeacherToDtoList(teacherRepository.findAll());
    }


    @Transactional(readOnly = true)
    public TeacherDto findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        Optional<Teacher> teacher = teacherRepository.findById(id);
        TeacherDto roleDto = null;
        if (teacher.isPresent()) {
            roleDto = TeacherMapper.fromTeacherToDto(teacher.get());
        }
        return roleDto;
    }


    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.deleteById(id);
    }

}
