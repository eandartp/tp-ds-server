package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.domain.JobSystem;
import it.tacticalpeople.tpdsserver.dto.JobSystemDto;
import it.tacticalpeople.tpdsserver.mapper.JobSystemMapper;
import it.tacticalpeople.tpdsserver.repository.JobSystemRepository;
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
public class JobSystemService {

    private final Logger log = LoggerFactory.getLogger(JobSystemService.class);

    @Autowired
    private JobSystemRepository jobSystemRepository;

    @Autowired
    private JobSystemMapper mapper;

    /**
     * Save a Job System.
     * @param jobSystemDto to transform into entity and save.
     * @return the persisted entity
     */
    public JobSystem save(JobSystemDto jobSystemDto)
    {
        log.debug("Request to save JobSystem : {}", jobSystemDto);
        JobSystem jobSystem = this.mapper.fromDtoToJobSystem(jobSystemDto);
        return jobSystemRepository.save(jobSystem);
    }

    /**
     * Partially update a Job System
     * @param jobSystemDto to transform into entity to update partially.
     * @return the persisted entity
     */
    public Optional<JobSystem> partialUpdate(JobSystemDto jobSystemDto)
    {
        log.debug("Request to partially update JobSystem : {}", jobSystemDto);
        return jobSystemRepository
                .findById(jobSystemDto.getId())
                .map(existingJobSystem -> {
                    if(jobSystemDto.getDescription() != null)
                        existingJobSystem.setDescription(jobSystemDto.getDescription());
                    return existingJobSystem;
                }).map(jobSystemRepository::save);
    }

    /**
     * Get all the Job Systems
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<JobSystemDto> findAll()
    {
        log.debug("Request to get all JobSystems");
        return jobSystemRepository.findAll().stream()
                .map(jobSystem -> mapper.fromJobSystemToDto(jobSystem))
                .collect(Collectors.toList());
    }

    /**
     * Get one Job System by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public JobSystemDto findOne(Long id)
    {
        log.debug("Request to get JobSystem : {}", id);
        Optional<JobSystem> jobSystem = jobSystemRepository.findById(id);
        JobSystemDto jobSystemDto = null;
        if(jobSystem.isPresent())
            jobSystemDto = this.mapper.fromJobSystemToDto(jobSystem.get());
        return jobSystemDto;
    }


    /**
     * Delete the Job System by id.
     * @param id the id of the entity.
     */
    public void delete(Long id)
    {
        log.debug("Request to delete JobSystem : {}", id);
        jobSystemRepository.deleteById(id);
    }

}
