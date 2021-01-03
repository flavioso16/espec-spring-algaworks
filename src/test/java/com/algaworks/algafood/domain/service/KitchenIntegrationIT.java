package com.algaworks.algafood.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class KitchenIntegrationIT {

	@Autowired
	private KitchenService kitchenService;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private KitchenRepository kitchenRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Before
	public void setUp() {
		RestAssured.basePath = "kitchens";
		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void shouldSaveWhenKitchenIsValid() {
		Kitchen kitchen = Kitchen.builder().name("Chinesa").build();

		kitchen = kitchenService.save(kitchen);

		assertThat(kitchen).isNotNull();
		assertThat(kitchen.getId()).isNotNull();
	}

	@Test(expected = ConstraintViolationException.class)
	public void shouldFailWhenSaveKitchenWithoutName() {
		Kitchen kitchen = Kitchen.builder().build();
		
		kitchenService.save(kitchen);
	}

	@Test(expected = EntityInUseException.class)
	public void shouldFailWhenDeleteInUseKitchen() {
		Kitchen thai = kitchenRepository.findByName("Tailandesa").get();
		kitchenService.delete(thai.getId());
	}

	@Test(expected = EntityNotFoundException.class)
	public void shouldFailWhenDeleteNonexistentKitchen() {
		Long ID_NOEXISTENT_KITCHEN = 99999L;
		kitchenService.delete(ID_NOEXISTENT_KITCHEN);
	}

	private void prepararDados() {
		final Kitchen thaiKitchen = kitchenRepository.save(Kitchen.builder().name("Tailandesa").build());
		restaurantRepository.save(Restaurant.builder()
				.name("Thai Gourmet")
				.shippingFee(BigDecimal.TEN)
				.kitchen(thaiKitchen)
				.build());
		kitchenRepository.save(Kitchen.builder().name("Americana").build());
	}

}
