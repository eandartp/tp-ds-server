package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Sede;
import it.tacticalpeople.tpdsserver.dto.SedeDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SedeMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public Sede fromDtoToSede(SedeDto sedeDto){
        Sede sede = new Sede();
        sede.setId(sedeDto.getId());
        sede.setSiteCity(sedeDto.getSiteCity());
        sede.setSiteAddress(sedeDto.getSiteAddress());
        sede.setSiteNumber(sedeDto.getSiteNumber());
        sede.setZp(sedeDto.getZp());
        sede.setProv(sedeDto.getProv());

        return sede;

    }

    public SedeDto fromSedeToDto(Sede sede){
        SedeDto sedeDto = new SedeDto();
        sedeDto.setId(sede.getId());
        sedeDto.setSiteCity(sede.getSiteCity());
        sedeDto.setSiteAddress(sede.getSiteAddress());
        sedeDto.setSiteNumber(sede.getSiteNumber());
        sedeDto.setZp(sede.getZp());
        sedeDto.setProv(sede.getProv());

        return sedeDto;
    }

}
