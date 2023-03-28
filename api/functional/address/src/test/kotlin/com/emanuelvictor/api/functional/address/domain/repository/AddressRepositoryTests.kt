package com.emanuelvictor.api.functional.address.domain.repository

import com.emanuelvictor.api.functional.address.domain.model.address.City
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

/**
 *
 */
@SpringBootTest
class AddressRepositoryTests(@Autowired val addressRepository: AddressRepository) {

    /**
     *
     */
    @Test
    fun `Must list cities by name of city and abbreviation from state`() {
        val nameOfCity = "Amapá"
        val abbreviationFromState = "AP"

        val city: Optional<City> = this.addressRepository.listCitiesByNameAndAbbreviationFromState(nameOfCity, abbreviationFromState)

        Assertions.assertThat(city.isPresent).isTrue
        Assertions.assertThat(city.get().name).isEqualTo(nameOfCity)
        Assertions.assertThat(city.get().state.abbreviation).isEqualTo(abbreviationFromState)
    }
}