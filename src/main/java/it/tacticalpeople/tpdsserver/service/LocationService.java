package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.domain.Location;
import it.tacticalpeople.tpdsserver.dto.LocationDto;
import it.tacticalpeople.tpdsserver.mapper.LocationMapper;
import it.tacticalpeople.tpdsserver.repository.LocationRepository;
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
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper mapper;

    public Location save(LocationDto locationDto)
    {
        log.debug("Request to save Location : {}", locationDto);
        Location location = this.mapper.fromDtoToLocation(locationDto);
        return locationRepository.save(location);
    }

    public Optional<Location> partialUpdate(LocationDto locationDto)
    {
        log.debug("Request to partially update Location : {}", locationDto);
        return locationRepository
                .findById(locationDto.getId())
                .map(existingLocation -> {
                    if(locationDto.getAddress() != null)
                        existingLocation.setAddress(locationDto.getAddress());
                    if(locationDto.getCity() != null)
                        existingLocation.setCity(locationDto.getCity());
                    if(locationDto.getPostalCode() != null)
                        existingLocation.setPostalCode(locationDto.getPostalCode());
                    if(locationDto.getProvince() != null)
                        existingLocation.setProvince(locationDto.getProvince());
                    if(locationDto.getState() != null)
                        existingLocation.setState(locationDto.getState());
                    if(locationDto.getReferenceName() != null)
                        existingLocation.setReferenceName(locationDto.getReferenceName());
                    if(locationDto.getEmail() != null)
                        existingLocation.setEmail(locationDto.getEmail());

                    return existingLocation;
                }).map(locationRepository::save);
    }

    @Transactional(readOnly = true)
    public List<LocationDto> findAll()
    {
        log.debug("Request to get all Locations");
        return locationRepository.findAll().stream()
                .map(location -> mapper.fromLocationToDto(location))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LocationDto findOne(Long id)
    {
        log.debug("Request to get Location : {}", id);
        Optional<Location> jobSystem = locationRepository.findById(id);
        LocationDto jobSystemDto = null;
        if(jobSystem.isPresent())
            jobSystemDto = this.mapper.fromLocationToDto(jobSystem.get());
        return jobSystemDto;
    }

    public void delete(Long id)
    {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }

}
