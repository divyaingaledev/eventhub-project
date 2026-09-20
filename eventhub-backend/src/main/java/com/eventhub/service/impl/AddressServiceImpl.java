package com.eventhub.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dto.AddressRequest;
import com.eventhub.dto.AddressResponse;
import com.eventhub.entity.Address;
import com.eventhub.entity.User;
import com.eventhub.mapper.AddressMapper;
import com.eventhub.repository.AddressRepository;
import com.eventhub.repository.UserRepository;
import com.eventhub.service.AddressService;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    
    // CONSTRUCTOR
   

    public AddressServiceImpl(
            AddressRepository addressRepository,
            UserRepository userRepository,
            AddressMapper addressMapper) {

        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.addressMapper = addressMapper;
    }

    // CREATE ADDRESS
   
    @Override
    public AddressResponse createAddress(
            Long userId,
            AddressRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(
                );

        Address address =
                addressMapper.toEntity(request);

        // Set User relationship
        address.setUser(user);

        Address savedAddress =
                addressRepository.save(address);

        return addressMapper.toResponse(
                savedAddress
        );
    }

   
    // GET ADDRESS BY ID
    

    @Override
    @Transactional(readOnly = true)
    public AddressResponse getAddressById(
            Long id) {

        Address address =
                addressRepository.findById(id)
                .orElseThrow(
                );

        return addressMapper.toResponse(
                address
        );
    }

   
    // GET CURRENT USER ADDRESSES
 

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getMyAddresses() {

        /*
         * JWT Security will be implemented later.
         *
         * After JWT implementation, the current
         * authenticated user's ID will be obtained
         * from Spring Security SecurityContext.
         */

        throw new UnsupportedOperationException(
                "Current user lookup will be implemented with JWT Security"
        );
    }

   
    // GET ADDRESSES BY USER ID
   
    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByUser(
            Long userId) {

        // Check user exists
       
        return addressRepository
                .findByUserId(userId)
                .stream()
                .map(addressMapper::toResponse)
                .collect(Collectors.toList());
    }

    // UPDATE ADDRESS
    
    @Override
    public AddressResponse updateAddress(
            Long id,
            AddressRequest request) {

        Address address =
                addressRepository.findById(id)
                .orElseThrow(
                );

        // Update address fields

      

        address.setCity(
                request.getCity()
        );

        address.setState(
                request.getState()
        );

        address.setPincode(
                request.getPincode()
        );

        Address updatedAddress =
                addressRepository.save(address);

        return addressMapper.toResponse(
                updatedAddress
        );
    }

    
    // DELETE ADDRESS
    @Override
    public void deleteAddress(
            Long id) {

        Address address =
                addressRepository.findById(id)
                .orElseThrow(
                );

        addressRepository.delete(address);
    }
}