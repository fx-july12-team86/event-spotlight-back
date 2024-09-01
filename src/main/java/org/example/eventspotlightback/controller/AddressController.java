package org.example.eventspotlightback.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.address.AddAddressDto;
import org.example.eventspotlightback.dto.internal.address.AddressDto;
import org.example.eventspotlightback.service.address.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto addAddress(@RequestBody @Valid AddAddressDto addAddressDto) {
        return addressService.addAddress(addAddressDto);
    }

    @GetMapping
    public List<AddressDto> findAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public AddressDto findAddressById(@PathVariable long id) {
        return addressService.findAddressById(id);
    }

    @PutMapping("/{id}")
    public AddressDto updateAddress(@PathVariable long id,
                                      @RequestBody @Valid AddAddressDto updateAddressDto) {
        return addressService.updateAddress(id, updateAddressDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable long id) {
        addressService.deleteAddressById(id);
    }
}
