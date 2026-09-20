package com.eventhub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.AddressRequest;
import com.eventhub.dto.AddressResponse;
import com.eventhub.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

  
    // CONSTRUCTOR
  
    public AddressController(
            AddressService addressService) {

        this.addressService = addressService;
    }

    
    // CREATE ADDRESS FOR USER
    // POST /api/addresses/user/{userId}
    

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponse> createAddress(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest request) {

        AddressResponse response =
                addressService.createAddress(
                        userId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    
    // GET ADDRESS BY ID
    // GET /api/addresses/{id}
    

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(
            @PathVariable Long id) {

        AddressResponse response =
                addressService.getAddressById(id);

        return ResponseEntity.ok(response);
    }

    
    // GET ADDRESSES BY USER
    // GET /api/addresses/user/{userId}
    

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByUser(
            @PathVariable Long userId) {

        List<AddressResponse> addresses =
                addressService.getAddressesByUser(userId);

        return ResponseEntity.ok(addresses);
    }

    
    // GET MY ADDRESSES
    // GET /api/addresses/my


    @GetMapping("/my")
    public ResponseEntity<List<AddressResponse>> getMyAddresses() {

        List<AddressResponse> addresses =
                addressService.getMyAddresses();

        return ResponseEntity.ok(addresses);
    }

    
    // UPDATE ADDRESS
    // PUT /api/addresses/{id}
    

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request) {

        AddressResponse response =
                addressService.updateAddress(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }

    
    // DELETE ADDRESS
    // DELETE /api/addresses/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long id) {

        addressService.deleteAddress(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}