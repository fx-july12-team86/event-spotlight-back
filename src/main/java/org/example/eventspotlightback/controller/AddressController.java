package org.example.eventspotlightback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.service.address.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Address management", description = "Endpoint for managing Addresses")
@RequiredArgsConstructor
@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    @Operation(
            summary = "Add new Address"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto addAddress(@RequestBody @Valid AddAddressDto addAddressDto) {
        return addressService.addAddress(addAddressDto);
    }

    @Operation(
            summary = "Update existing Address"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public AddressDto updateAddress(@PathVariable Long id,
                                    @RequestBody @Valid AddAddressDto updateAddressDto) {
        return addressService.updateAddress(id, updateAddressDto);
    }

    @Operation(
            summary = "Delete Address"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddressById(id);
    }

    @Operation(
            summary = "Find all existing addresses"
    )
    @GetMapping
    public List<AddressDto> findAllAddresses() {
        return addressService.findAll();
    }

    @Operation(
            summary = "Find address by id"
    )
    @GetMapping("/{id}")
    public AddressDto findAddressById(@PathVariable Long id) {
        return addressService.findAddressById(id);
    }
}
