package com.eventhub.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.dto.AddressRequest;
import com.eventhub.dto.AddressResponse;
import com.eventhub.entity.Address;

@Component
public class AddressMapper {

    // DTO → Entity
    public Address toEntity(AddressRequest request) {

        Address address = new Address();

        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
   

        return address;
    }

    // Entity → Response DTO
    public AddressResponse toResponse(Address address) {

        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPincode(),
                address.getCountry()
        );
    }
}