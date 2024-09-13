package com.emanuelvictor.api.functional.address.application.resources;

import com.emanuelvictor.api.functional.address.domain.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Emanuel Victor
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CitiesResourceTests {

    /**
     *
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     *
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     *
     */
    @Autowired
    private AddressRepository addressRepository;

    /**
     * @throws Exception exception
     */
    @Test
    public void getCityFromAddressEndpoint() throws Exception {
        final var nameOfCity = "Amapá";
        final var abbreviationFromState = "AP";
        final var city = addressRepository.listCitiesByNameAndAbbreviationFromState(nameOfCity, abbreviationFromState).orElseThrow();
        final var jsonExpected = objectMapper.writeValueAsString(city);
        final var charsetExpected = StandardCharsets.UTF_8;

        final var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/cities")
                .queryParam("nameOfCity", nameOfCity)
                .queryParam("abbreviationFromState", abbreviationFromState)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        );

        result.andExpect(status().isOk())
                .andExpect(content().encoding(charsetExpected))
                .andExpect(content().string(jsonExpected));
    }
}
