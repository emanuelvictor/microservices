package com.emanuelvictor.api.functional.address.application.resources;

import com.emanuelvictor.api.functional.address.domain.model.address.City;
import com.emanuelvictor.api.functional.address.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Emanuel Victor
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/cities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CitiesResource {

    /**
     *
     */
    private final AddressRepository addressRepository;

    /**
     * TODO must return
     *
     * @param nameOfCity            String
     * @param abbreviationFromState String
     * @return {@link Optional<City>}
     */
    @GetMapping
    public Optional<City> listCitiesByNameAndAbbreviationFromState(@RequestParam final String nameOfCity, @RequestParam final String abbreviationFromState) {
        return addressRepository.listCitiesByNameAndAbbreviationFromState(nameOfCity, abbreviationFromState);
    }
}
