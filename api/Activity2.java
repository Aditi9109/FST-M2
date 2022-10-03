package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;


public class Activity2 {

    String baseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority=1)
    public void addNewUserFromFile() throws IOException {

        FileInputStream inputJSON = new FileInputStream("src/test/java/activities/user.json");
        String reqBody = new String(inputJSON.toString());

        Response response =
                given().contentType(ContentType.JSON)
                        .body(reqBody)
                        .when().post(baseURI); // Send POST request

        inputJSON.close();

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("9901"));
    }

    @Test(priority=2)
    public void getUserInfo() {

        File outputJSON = new File("src/test/java/activities/userGETResponse.json");
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .pathParam("username", "Aditi1990") // Pass request body from file
                        .when().get(baseURI + "/{username}"); // Send POST request

        // Get response body
        String resBody = response.getBody().asPrettyString();

        // Assertion
        response.then().body("id", equalTo(9901));
        response.then().body("username", equalTo("Aditi1990"));
        response.then().body("firstName", equalTo("Aditi"));
        response.then().body("lastName", equalTo("Singh"));
        response.then().body("email", equalTo("aditisingh@example.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9999999999"));
    }

    @Test(priority=3)
    public void deleteUser(){
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .pathParam("username", "Aditi1990") // Add path parameter
                        .when().delete(baseURI + "/{username}"); // Send POST request

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("Aditi1990"));
    }
}
