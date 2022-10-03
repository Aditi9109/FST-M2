package liveProject;


import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    //Header Object
    Map<String, String> headers = new HashMap<>();

    //Resource Path
    String resourcePath = "/api/users";

    //generate a contract
    @Pact(provider = "UserProvider", consumer = "UserConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        //Add headers
        headers.put("Content-Type", "application/json");

        //create json body for request and response
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id",123)
                .stringType("firstName","Aditi")
                .stringType("lastName","Singh")
                .stringType("email","aditi.singh3@ibm.com");

        //write the fragment to pact
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .method("POST")
                .headers(headers)
                .path(resourcePath)
                .body(requestResponseBody)
                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();


    }

    @Test
    @PactTestFor(providerName = "UserProvider",port = "8282")
    public void consumerTest()
    {
        //BaseURI
        String requestURI = "http://localhost:8282"+resourcePath;

        //Request Body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id",123);
        reqBody.put("firstName","Aditi");
        reqBody.put("lastName","Singh");
        reqBody.put("email","aditi.singh3@ibm.com");

        //generate Response
        given().headers(headers).body(reqBody).log().all()
                .when().post(requestURI)
                .then().statusCode(201).log().all();
    }



}
