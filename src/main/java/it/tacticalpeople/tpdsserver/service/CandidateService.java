package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.domain.Candidate;
import it.tacticalpeople.tpdsserver.dto.CandidateDto;
import it.tacticalpeople.tpdsserver.mapper.CandidateMapper;
import it.tacticalpeople.tpdsserver.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@Service
@Transactional
public class CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateService.class);

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateMapper candidateMapper;

    public Candidate save(CandidateDto candidateDto)
    {
        log.debug("Request to save Candidate : {}", candidateDto);
        Candidate candidate = this.candidateMapper.fromDtoToCandidate(candidateDto);
        return candidateRepository.save(candidate);
    }

    public Optional<Candidate> partialUpdate(CandidateDto candidateDto)
    {
        log.debug("Request to partially update Candidate : {}", candidateDto);
        return candidateRepository
                .findById(candidateDto.getId())
                .map(existingCandidate -> {
                    if(candidateDto.getFirstName() != null)
                        existingCandidate.setFirstName(candidateDto.getFirstName());
                    if(candidateDto.getLastName() != null)
                        existingCandidate.setLastName(candidateDto.getLastName());
                    if(candidateDto.getAddress() != null)
                        existingCandidate.setAddress(candidateDto.getAddress());
                    if(candidateDto.getZip() != null)
                        existingCandidate.setZip(candidateDto.getZip());
                    if(candidateDto.getCity() != null)
                        existingCandidate.setCity(candidateDto.getCity());
                    if(candidateDto.getFeedback() != null)
                        existingCandidate.setFeedback(candidateDto.getFeedback());
                    return existingCandidate;

                }).map(candidateRepository::save);
    }

    @Transactional(readOnly = true)
    public List<CandidateDto> findAll()
    {
        log.debug("Request to get all Candidates");
        return candidateRepository.findAll().stream()
                .map(candidate -> candidateMapper.fromCandidateToDto(candidate))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CandidateDto findOne(Long id)
    {
        log.debug("Request to get Candidate : {}", id);
        Optional<Candidate> candidate = candidateRepository.findById(id);
        CandidateDto candidateDto = null;
        if(candidate.isPresent())
            candidateDto = this.candidateMapper.fromCandidateToDto(candidate.get());
        return candidateDto;
    }

    public void delete(Long id)
    {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
    }

}
