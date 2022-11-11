package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Candidate;
import it.tacticalpeople.tpdsserver.dto.CandidateDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CandidateMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public Candidate fromDtoToCandidate(CandidateDto candidateDto){
        Candidate candidate = new Candidate();
        candidate.setId(candidateDto.getId());
        candidate.setFirstName(candidateDto.getFirstName());
        candidate.setLastName(candidateDto.getLastName());
        candidate.setAddress(candidateDto.getAddress());
        candidate.setZip(candidateDto.getZip());
        candidate.setCity(candidateDto.getCity());
        candidate.setFeedback(candidateDto.getFeedback());
        return candidate;
    }

    public CandidateDto fromCandidateToDto(Candidate candidate){
        CandidateDto candidateDto = new CandidateDto();
        candidateDto.setId(candidate.getId());
        candidateDto.setFirstName(candidate.getFirstName());
        candidateDto.setLastName(candidate.getLastName());
        candidateDto.setAddress(candidate.getAddress());
        candidateDto.setZip(candidate.getZip());
        candidateDto.setCity(candidate.getCity());
        candidateDto.setFeedback(candidate.getFeedback());
        return candidateDto;
    }
}
