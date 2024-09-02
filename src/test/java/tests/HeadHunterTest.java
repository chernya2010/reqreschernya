package tests;

import com.google.gson.Gson;
import headhunter_objects.VacanciesList;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HeadHunterTest {

    @Test
    public void qaAutomationSearchTest() {
        String body =
        given()
        .when()
                .get("https://api.hh.ru/vacancies?text=QA")

        .then()
        .log().all()
        .statusCode(200)
        .extract().body().asString();
        System.out.println("***BODY***");
        System.out.println(body);

        System.out.println("***OBJECT***");
        VacanciesList vacanciesList = new Gson().fromJson(body, VacanciesList.class);
        System.out.println(vacanciesList);
    }
}