package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Location;
import it.tacticalpeople.tpdsserver.dto.LocationDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class LocationMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public Location fromDtoToLocation(LocationDto locationDto){
        Location location = new Location();
        location.setId(locationDto.getId());
        location.setAddress(locationDto.getAddress());
        location.setCity(locationDto.getCity());
        location.setPostalCode(locationDto.getPostalCode());
        location.setProvince(locationDto.getProvince());
        location.setState(locationDto.getState());
        location.setReferenceName(locationDto.getReferenceName());
        location.setEmail(locationDto.getEmail());
        return location;
    }

    public LocationDto fromLocationToDto(Location location){
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setAddress(location.getAddress());
        locationDto.setCity(location.getCity());
        locationDto.setPostalCode(location.getPostalCode());
        locationDto.setProvince(location.getProvince());
        locationDto .setState(location.getState());
        locationDto.setReferenceName(location.getReferenceName());
        locationDto.setEmail(location.getEmail());

        return locationDto;
    }
}
