package com.eventhub.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dto.VenueRequest;
import com.eventhub.dto.VenueResponse;
import com.eventhub.entity.Venue;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.mapper.VenueMapper;
import com.eventhub.repository.VenueRepository;
import com.eventhub.service.VenueService;

@Service
@Transactional
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    public VenueServiceImpl(
            VenueRepository venueRepository,
            VenueMapper venueMapper) {

        this.venueRepository = venueRepository;
        this.venueMapper = venueMapper;
    }

    @Override
    public VenueResponse createVenue(VenueRequest request) {

        Venue venue = venueMapper.toEntity(request);

        Venue savedVenue = venueRepository.save(venue);

        return venueMapper.toResponse(savedVenue);
    }

    @Override
    @Transactional(readOnly = true)
    public VenueResponse getVenueById(Long id) {

        Venue venue = venueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Venue not found with id: " + id
                        )
                );

        return venueMapper.toResponse(venue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VenueResponse> getAllVenues() {

        return venueRepository.findAll()
                .stream()
                .map(venueMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VenueResponse updateVenue(
            Long id,
            VenueRequest request) {

        Venue venue = venueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Venue not found with id: " + id
                        )
                );

        venueMapper.updateEntity(venue, request);

        Venue updatedVenue = venueRepository.save(venue);

        return venueMapper.toResponse(updatedVenue);
    }

    @Override
    public void deleteVenue(Long id) {

        Venue venue = venueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Venue not found with id: " + id
                        )
                );

        venueRepository.delete(venue);
    }
}