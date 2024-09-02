package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

//https://www.onliner.by/sdapi/kurs/api/bestrate?currency=USD&type=nbrb
public class OnlinerTest {
    /**
     * Get currency uds test.
     */
    @Test
    public void getCurrencyUDSTest(){
        given()
        .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=USD&type=nbrb")
        .then()
                .statusCode(200);
    }

    /**
     * Get currency eur test.
     */
    @Test
    public void getCurrencyEURTest(){
        given()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=EUR&type=nbrb")
                .then()
                .statusCode(200);
    }

    /**
     * Get currency rub test.
     */
    @Test
    public void getCurrencyRUBTest(){
        given()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=RUB&type=nbrb")
                .then()
                .statusCode(200);
    }

    /**
     * Get currency usd amount test.
     */
    @Test
    public void getCurrencyUSDAmountTest(){
        given()
                .log().all()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=USD&type=nbrb")
                .then()
                .log().all()
                .body("amount", equalTo("3,1968"))
                .statusCode(200);
    }
}