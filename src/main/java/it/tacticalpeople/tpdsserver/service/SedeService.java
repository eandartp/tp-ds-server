package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.domain.Sede;
import it.tacticalpeople.tpdsserver.dto.SedeDto;
import it.tacticalpeople.tpdsserver.mapper.SedeMapper;
import it.tacticalpeople.tpdsserver.repository.SedeRepository;
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
public class SedeService {

    private final Logger log = LoggerFactory.getLogger(SedeService.class);

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private SedeMapper mapper;

    public Sede save(SedeDto sedeDto){
        log.debug("Request to save Sede: {}", sedeDto);
        Sede sede = this.mapper.fromDtoToSede(sedeDto);
        return sedeRepository.save(sede);
    }

    public Optional<Sede> partialUpdate(SedeDto sedeDto){
        log.debug("Request to partially update Sede: {}", sedeDto);
        return sedeRepository
                .findById(sedeDto.getId())
                .map(existingSede -> {
                    if(sedeDto.getSiteCity() != null)
                        existingSede.setSiteCity(sedeDto.getSiteCity());
                    if(sedeDto.getSiteAddress() != null)
                        existingSede.setSiteAddress(sedeDto.getSiteAddress());
                    if(sedeDto.getSiteNumber() != null)
                        existingSede.setSiteNumber(sedeDto.getSiteNumber());
                    if(sedeDto.getZp() != null)
                        existingSede.setZp(sedeDto.getZp());
                    if(sedeDto.getProv() != null)
                        existingSede.setProv(sedeDto.getProv());

                    return existingSede;

                }).map(sedeRepository::save);
    }

    public List<SedeDto> findAll(){
        log.debug("Request to get all sede");
        return sedeRepository.findAll().stream()
                .map(sede -> mapper.fromSedeToDto(sede))
                .collect(Collectors.toList());
    }

    public SedeDto findOne(Long id){
        log.debug("Request to get sede: {}", id);
        Optional<Sede> sede = sedeRepository.findById(id);
        SedeDto sedeDto = null;
        if(sede.isPresent())
            sedeDto= this.mapper.fromSedeToDto(sede.get());
        return sedeDto;
    }

    public void delete(Long id){
        log.debug("Request to delete sede: {}", id);
        sedeRepository.deleteById(id);
    }

}
