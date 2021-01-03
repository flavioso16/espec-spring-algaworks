package com.algaworks.algafood.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

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
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * @author flaoliveira
 * @version : $<br/>
 * : $
 * @since 1/3/21 10:41 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class RestaurantIntegrationApiIT {

    private static final String BUSINESS_ERROR = "Violação de regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private static final int RESTAURANT_NOEXISTENT_ID = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private String jsonRestaurant;
    private String jsonRestaurantWithoutFee;
    private String jsonRestaurantWithoutKitchen;
    private String jsonRestaurantWithNoexistentKitchen;

    private Restaurant burgerTopRestaurant;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        jsonRestaurant = ResourceUtils.getContentFromResource(
                "/json/restaurant-new-york-barbecue.json");

        jsonRestaurantWithoutFee = ResourceUtils.getContentFromResource(
                "/json/restaurant-new-york-barbecue-without-fee.json");

        jsonRestaurantWithoutKitchen = ResourceUtils.getContentFromResource(
                "/json/restaurant-new-york-barbecue-without-kitchen.json");

        jsonRestaurantWithNoexistentKitchen = ResourceUtils.getContentFromResource(
                "/json/restaurant-new-york-barbecue-with-noexistent-kitchen.json");

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void shouldReturnStatus200WhenGetRestaurant() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnStatus201WhenSaveRestaurant() {
        given()
            .body(jsonRestaurant)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnStatus400WhenSaveRestaurantWithoutFeeTax() {
        given()
                .body(jsonRestaurantWithoutFee)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void shouldReturnStatus400WhenSaveRestaurantWithoutKitchen() {
        given()
                .body(jsonRestaurantWithoutKitchen)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void shouldReturnStatus400WhenSaveRestaurantWithNoexistentKitchen() {
        given()
                .body(jsonRestaurantWithNoexistentKitchen)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(BUSINESS_ERROR));
    }

    @Test
    public void shouldReturnStatusOkWhenGetRestaurant() {
        given()
                .pathParam("restaurantId", burgerTopRestaurant.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(burgerTopRestaurant.getName()));
    }

    @Test
    public void shouldReturnStatus404WhenGetNoexistentRestaurant() {
        given()
                .pathParam("restaurantId", RESTAURANT_NOEXISTENT_ID)
                .accept(ContentType.JSON)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        final Kitchen brasilKitchen = Kitchen.builder().name("Brasileira").build();
        kitchenRepository.save(brasilKitchen);
        final Kitchen americanKitchen = Kitchen.builder().name("Americana").build();
        kitchenRepository.save(americanKitchen);

        burgerTopRestaurant = Restaurant.builder()
                .name("Burger Top")
                .shippingFee(BigDecimal.TEN)
                .kitchen(americanKitchen)
                .build();
        restaurantRepository.save(burgerTopRestaurant);

        final Restaurant comidaMineiraRestaurant = Restaurant.builder()
                .name("Comida Mineira")
                .shippingFee(BigDecimal.TEN)
                .kitchen(brasilKitchen)
                .build();

        restaurantRepository.save(comidaMineiraRestaurant);

        restaurantRepository.findAll();
    }

}
