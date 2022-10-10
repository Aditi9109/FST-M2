package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubRESTAssuredProject {

    int extractId;
    // Base uri
    String baseURI = "https://api.github.com";
    String token = "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";
    String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDIBCAMKZZjAgCs4TCTtGf1cqszttNDnPjCqPCVgi7/kCLadxBGWV9z5FV68fImq2q/ao9e9PEVPh7T/v0nP3x4kZ9S0TG7KdAY8wBjM+kw5zo3lXS4/YAawxZJT7A8HZ/swc5CDdsUt6oaf3HRDOMa15faQHcHhGYwgXmeJg+Eg8ZMB9KGZVpEF2AA4yoHPn68AWaZgymuLBk6aEX3VCUXOKwaSIs4An5OJG6u0nkAuwvsga9JlGdp/Ppvz//WzCWbug0t0VWXkNnGgzhHfD0ak06r4q81IwteZKwQ1aiZptSVu6CEQVU21Qv7BcFtE9U3lW3GOxBaYhEjR1NMY77f";

    //Request and response specifications
    RequestSpecification requestSpec;


    @BeforeClass
    public void Setup()
    {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "token "+token)
                .build();

    }

    @Test(priority = 1)
    public void postSSHTest()
    {
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("title", "TestAPIKey");
        reqBody.put("key", sshKey);

        //Generate response
        Response response = given().spec(requestSpec).body(reqBody).when().post("/user/keys");

        System.out.println(response.getBody().asPrettyString());
        //extract the id of the SSH key
        extractId = response.then().extract().path("id");
        System.out.println(extractId);

        // Assertion
        response.then().statusCode(201).body("key",equalTo(sshKey));
    }

    @Test(priority = 2)
    public void getSSHTest()
    {
        Response response =given().spec(requestSpec)
                        .pathParam("keyId", sshKey)
                        .when().get(baseURI + "/user/keys/{keyId}");

        System.out.println(response.getBody().asPrettyString());

        //Reporter.log(response.asString());
        response.then().statusCode(200).body("key",equalTo(sshKey));
    }

    @Test(priority = 3)
    public void deleteSSHTest()
    {
        Response response =given().spec(requestSpec)
                .pathParam("keyId", sshKey)
                .when().delete(baseURI + "/user/keys/{keyId}");

        response.then().statusCode(204);
    }

}
