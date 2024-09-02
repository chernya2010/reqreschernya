package tests;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import reqres_objects.User;
import reqres_objects.UserForRegisterAndLogin;
import reqres_objects.UsersList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Homework {
    public static final String BASE_URL = "https://reqres.in/api/";

    /**
     * Gets single resource not found test.
     */
    @Test(description = "Single resource not found and check status code")
    public void getSingleResourceNotFoundTest() {
        given()
                .log().all()
                .when().get(BASE_URL + "unknown/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    /**
     * Patch single user test.
     */
    @Test(description = "Update single user(patch) and check status code and all exist fields")
    public void patchSingleUserTest() {
        User user = User.builder()
                .name("Kermit")
                .job("TheFrog")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .when().patch(BASE_URL + "users/2")
                .then()
                .log().all()
                .body("name", equalTo(user.getName()),
                        "job", equalTo(user.getJob()))
                .statusCode(200);
    }

    /**
     * Delete single user test.
     */
    @Test(description = "Delete single user and check status code")
    public void deleteSingleUserTest() {
        given()
                .log().all()
                .when().delete(BASE_URL + "users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    /**
     * Post successful register user test.
     */
    @Test(description = "Successful register new user, check status code, id field, token field")
    public void postSuccessfulRegisterUserTest() {
        UserForRegisterAndLogin userForRegisterAndLogin = UserForRegisterAndLogin.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        given()
                .body(userForRegisterAndLogin)
                .header("Content-type", "application/json")
                .log().all()
                .when().post(BASE_URL + "register")
                .then()
                .log().all()
                .body("id", equalTo(4),
                        "token", equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    /**
     * Post unsuccessful register user test.
     */
    @Test(description = "Unsuccessful register new user, check status code, error field")
    public void postUnsuccessfulRegisterUserTest() {
        UserForRegisterAndLogin userForRegisterAndLogin = UserForRegisterAndLogin.builder()
                .email("sydney@fife")
                .build();
        given()
                .body(userForRegisterAndLogin)
                .header("Content-type", "application/json")
                .log().all()
                .when().post(BASE_URL + "register")
                .then()
                .log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }

    /**
     * Post successful login test.
     */
    @Test(description = "Successful login, check status code, token field")
    public void postSuccessfulLoginTest() {
        UserForRegisterAndLogin userForRegisterAndLogin = UserForRegisterAndLogin.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        given()
                .body(userForRegisterAndLogin)
                .header("Content-type", "application/json")
                .log().all()
                .when().post(BASE_URL + "login")
                .then()
                .log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    /**
     * Post unsuccessful login test.
     */
    @Test(description = "Unsuccessful login, check status code, token field")
    public void postUnsuccessfulLoginTest() {
        UserForRegisterAndLogin userForRegisterAndLogin = UserForRegisterAndLogin.builder()
                .email("peter@klaven")
                .build();
        given()
                .body(userForRegisterAndLogin)
                .header("Content-type", "application/json")
                .log().all()
                .when().post(BASE_URL + "login")
                .then()
                .log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }

    /**
     * Gets users list with delay test.
     */
    @Test(description = "Get list of users with delay and check status code and all exist fields of the first user")
    public void getUsersListWithDelayTest() {
        String body =
                given()
                        .log().all()
                        .when().get(BASE_URL + "users?delay=3")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();
        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        Assert.assertEquals(usersList.getData().get(0).getId(), 1);
        Assert.assertEquals(usersList.getData().get(0).getEmail(), "george.bluth@reqres.in");
        Assert.assertEquals(usersList.getData().get(0).getFirstName(), "George");
        Assert.assertEquals(usersList.getData().get(0).getLastName(), "Bluth");
        Assert.assertEquals(usersList.getData().get(0).getAvatar(), "https://reqres.in/img/faces/1-image.jpg");
    }
}