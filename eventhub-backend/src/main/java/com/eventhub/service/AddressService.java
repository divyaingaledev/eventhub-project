package com.eventhub.service;

import java.util.List;

import com.eventhub.dto.AddressRequest;
import com.eventhub.dto.AddressResponse;

public interface AddressService {

    // Create address for user
    AddressResponse createAddress(
            Long userId,
            AddressRequest request
    );

    // Get address by ID
    AddressResponse getAddressById(
            Long id
    );

    // Get current logged-in user's addresses
    List<AddressResponse> getMyAddresses();

    // Get addresses by user ID
    List<AddressResponse> getAddressesByUser(
            Long userId
    );

    // Update address
    AddressResponse updateAddress(
            Long id,
            AddressRequest request
    );

    // Delete address
    void deleteAddress(
            Long id
    );
}