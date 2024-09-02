package tests;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import reqres_objects.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqResTests {

    public static final String BASE_URL = "https://reqres.in/api/";

    /**
     * Post create user test.
     */
    @Test(description = "Create user, check status code, name field, job field")
    public void postCreateUserTest(){

        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();

        given()
                .body(user)
                .header("Content-type", "application/json")
                .log().all()
                .when().post(BASE_URL + "users")
                .then()
                .log().all()
                .body("name", equalTo(user.getName()),
                        "job", equalTo(user.getJob()))
                .statusCode(201);
    }

    /**
     * Get users list test.
     */
    @Test(description = "Get list of users and check status code and all exist fields of the first user")
    public void getUsersListTest(){
        String body =
        given()
                .log().all()
                .when().get(BASE_URL + "users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        Assert.assertEquals(usersList.getData().get(0).getId(), 7);
        Assert.assertEquals(usersList.getData().get(0).getEmail(), "michael.lawson@reqres.in");
        Assert.assertEquals(usersList.getData().get(0).getFirstName(), "Michael");
        Assert.assertEquals(usersList.getData().get(0).getLastName(), "Lawson");
        Assert.assertEquals(usersList.getData().get(0).getAvatar(), "https://reqres.in/img/faces/7-image.jpg");
    }

    @Test(description = "Get single user and check status code and all exist fields")
    public void getSingleUserTest(){
        String body =
                given()
                        .log().all()
                        .when().get(BASE_URL + "users/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();
        SingleUser singleUser = new Gson().fromJson(body, SingleUser.class);
        Assert.assertEquals(singleUser.getData().getId(), 2);
        Assert.assertEquals(singleUser.getData().getEmail(), "janet.weaver@reqres.in");
        Assert.assertEquals(singleUser.getData().getFirstName(), "Janet");
        Assert.assertEquals(singleUser.getData().getLastName(), "Weaver");
        Assert.assertEquals(singleUser.getData().getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
        Assert.assertEquals(singleUser.getSupport().getUrl(), "https://reqres.in/#support-heading");
        Assert.assertEquals(singleUser.getSupport().getText(), "To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test(description = "Single user not found and check status code")
    public void getSingleUserNotFoundTest(){
        given()
                .log().all()
                .when().get(BASE_URL + "users/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test(description = "Get list of resources and check status code and all exist fields of the first resource")
    public void getResourcesListTest(){
        String body =
                given()
                        .log().all()
                        .when().get(BASE_URL + "unknown")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();
        ResourcesList resourcesList = new Gson().fromJson(body, ResourcesList.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(resourcesList.getData().get(0).getId(), 1);
        softAssert.assertEquals(resourcesList.getData().get(0).getName(), "cerulean");
        softAssert.assertEquals(resourcesList.getData().get(0).getYear(), 2000);
        softAssert.assertEquals(resourcesList.getData().get(0).getColor(), "#98B2D1");
        softAssert.assertEquals(resourcesList.getData().get(0).getPantoneValue(), "15-4020");
        softAssert.assertAll();
    }

    @Test(description = "Get single resource and check status code and all exist fields")
    public void getSingleResourceTest(){
        String body =
                given()
                        .log().all()
                        .when().get(BASE_URL + "unknown/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();
        SingleResource singleResource = new Gson().fromJson(body, SingleResource.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(singleResource.getData().getId(), 2);
        softAssert.assertEquals(singleResource.getData().getName(), "fuchsia rose");
        softAssert.assertEquals(singleResource.getData().getYear(), 2001);
        softAssert.assertEquals(singleResource.getData().getColor(), "#C74375");
        softAssert.assertEquals(singleResource.getData().getPantoneValue(), "17-2031");
        softAssert.assertEquals(singleResource.getSupport().getUrl(), "https://reqres.in/#support-heading");
        softAssert.assertEquals(singleResource.getSupport().getText(), "To keep ReqRes free, contributions towards server costs are appreciated!");
        softAssert.assertAll();
    }

    @Test(description = "Update single user and check status code and all exist fields")
    public void updateSingleUserTest(){
        User user = User.builder()
                .name("")
                .job("")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
        .when().post(BASE_URL + "users/2")
                .then()
                .log().all();
        user = User.builder()
                .name("Kermit")
                .job("TheFrog")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
        .when().put(BASE_URL + "users/2")
                .then()
                .log().all()
                .body("name", equalTo(user.getName()),
                        "job", equalTo(user.getJob()))
                .statusCode(200);
    }
}