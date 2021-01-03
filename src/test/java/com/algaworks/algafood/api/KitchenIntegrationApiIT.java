package com.algaworks.algafood.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class KitchenIntegrationApiIT {

	public static final int NONEXISTENT_KITCHEN_ID = 100;
	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private KitchenRepository kitchenRepository;

	private Kitchen americanKitchen;

	private int amountKitchen;

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "kitchens";
		databaseCleaner.clearTables();
		prepareData();
	}

	private void prepareData() {
		kitchenRepository.save(Kitchen.builder().name("Tailandesa").build());
		americanKitchen = kitchenRepository.save(Kitchen.builder().name("Americana").build());

		amountKitchen = Long.valueOf(kitchenRepository.count()).intValue();
	}

	@Test
	public void shouldReturnStatusOKWhenGetKitchens() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void shouldReturnCorrectAmountKitchensWhenGetKitchens() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(amountKitchen))
			.body("name",  hasItems("Tailandesa", "Americana"));
	}

	@Test
	public void shouldReturnStatusOkWhenRegisterKitchen() {
		given()
			.body(ResourceUtils.getContentFromResource("/json/chinesa-kitchen.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void  shouldReturnStatusOkWhenGetExistentKitchen() {
		given()
			.pathParam("kitchenId", americanKitchen.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{kitchenId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(americanKitchen.getName()));
	}

	@Test
	public void  shouldReturnStatus404WhenGetNoexistentKitchen() {
		given()
				.pathParam("kitchenId", NONEXISTENT_KITCHEN_ID)
				.accept(ContentType.JSON)
				.when()
				.get("/{kitchenId}")
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

}
