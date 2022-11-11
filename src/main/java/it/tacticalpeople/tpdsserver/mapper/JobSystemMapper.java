package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.JobSystem;
import it.tacticalpeople.tpdsserver.domain.Location;
import it.tacticalpeople.tpdsserver.dto.JobSystemDto;
import it.tacticalpeople.tpdsserver.dto.LocationDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class JobSystemMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public JobSystem fromDtoToJobSystem(JobSystemDto jobSystemDto){
        JobSystem jobSystem = new JobSystem();
        jobSystem.setId(jobSystemDto.getId());
        jobSystem.setDescription(jobSystemDto.getDescription());
        return jobSystem;
    }

    public JobSystemDto fromJobSystemToDto(JobSystem jobSystem){
        JobSystemDto jobSystemDto = new JobSystemDto();
        jobSystemDto.setId(jobSystem.getId());
        jobSystemDto.setDescription(jobSystem.getDescription());
        return jobSystemDto;
    }

}
