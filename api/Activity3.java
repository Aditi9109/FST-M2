package activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Activity3 {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    Response response;
    String baseURI = "https://petstore.swagger.io/v2/pet";

    @BeforeClass
    public void setUp() {

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseURI)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status", equalTo("alive"))
                .build();
    }

    @DataProvider
    public Object[][] petInfoProvider() {
        // Setting parameters to pass to test case
        Object[][] testData = new Object[][] {
                { 77232, "Riley", "alive" },
                { 77233, "Hansel", "alive" }
        };
        return testData;
    }

    @Test(priority=1)
    public void addPets() {
        String reqBody = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";
        response = given().spec(requestSpec)
                .body(reqBody).when().post(baseURI);

        reqBody = "{\"id\": 77233, \"name\": \"Hansel\", \"status\": \"alive\"}";
        response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();

        // Assertions
        response.then().spec(responseSpec);
    }

    // Test case using a DataProvider
    @Test(dataProvider = "petInfoProvider", priority=2)
    public void getPets(int id, String name, String status) {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("petId", id) // Add path parameter
                .when().get("/{petId}"); // Send GET request

        // Print response
        System.out.println(response.asPrettyString());
        // Assertions
        response.then()
                .spec(responseSpec) // Use responseSpec
                .body("name", equalTo(name)); // Additional Assertion
    }

    // Test case using a DataProvider
    @Test(dataProvider = "petInfoProvider", priority=3)
    public void deletePets(int id, String name, String status) {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("petId", id) // Add path parameter
                .when().delete("/{petId}"); // Send GET request

        // Assertions
        response.then().body("code", equalTo(200));
    }

}
