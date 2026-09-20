package com.eventhub.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.dto.VenueRequest;
import com.eventhub.dto.VenueResponse;
import com.eventhub.entity.Venue;

@Component
public class VenueMapper {

    // Convert request DTO to Venue entity
    public Venue toEntity(VenueRequest request) {

        Venue venue = new Venue();

        venue.setName(request.getName());
        venue.setAddress(request.getAddress());
        venue.setCity(request.getCity());
        venue.setState(request.getState());
        venue.setPincode(request.getPincode());
        venue.setCountry(request.getCountry());
        venue.setCapacity(request.getCapacity());

        return venue;
    }

    // Convert Venue entity to response DTO
    public VenueResponse toResponse(Venue venue) {

        if (venue == null) {
            return null;
        }

        VenueResponse response = new VenueResponse();

        response.setId(venue.getId());
        response.setName(venue.getName());
        response.setAddress(venue.getAddress());
        response.setCity(venue.getCity());
        response.setState(venue.getState());
        response.setPincode(venue.getPincode());
        response.setCountry(venue.getCountry());
        response.setCapacity(venue.getCapacity());

        return response;
    }

    // Update existing Venue entity
    public void updateEntity(
            Venue venue,
            VenueRequest request) {

        venue.setName(request.getName());
        venue.setAddress(request.getAddress());
        venue.setCity(request.getCity());
        venue.setState(request.getState());
        venue.setPincode(request.getPincode());
        venue.setCountry(request.getCountry());
        venue.setCapacity(request.getCapacity());
    }
}