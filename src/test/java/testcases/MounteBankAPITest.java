package testcases;

import base.Base;
import helpers.MountebankStubApi;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import listener.MyListener;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MounteBankAPITest extends MyListener {

    //Pre-requisite: Mountebank stub server is running and accessible

    //Validate that the stub server is running
    @Test(priority = 1)
    public void shouldHaveStatusCodeOf200ForStubServerAPI() {
        RestAssured.given().contentType(ContentType.JSON).get(Base.mountebankStubAPI).then().statusCode(200);
    }

    //Create an imposter on the stub server
    @Test(priority = 2)
    public void createSingleStubImposter() throws URISyntaxException {
        String filename = "teststub.json";
        File StubConfigFile = new File(Base.mountebankStubConfigPath + filename);

        try {
            String content = new String(Files.readAllBytes(StubConfigFile.toPath()), StandardCharsets.UTF_8);
            MountebankStubApi.createImposter(content, Base.mountebankStubServer).then()
                    .statusCode(201);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Validate the behaviour of the stub. It should return a 400 response given the request follows the predicates in the stub settings.
    @Test(priority = 3)
    public void validateImposter() {
        JSONObject requestParameters = new JSONObject();
        requestParameters.put("optionalField", "true");
        RestAssured
                .given()
                .contentType("application/json\r\n")
                .body(requestParameters.toString())
                .post(Base.mountebankStubServerDomain + ":5050/test")
                .then().statusCode(400);
    }

    //Delete the imposter
    @Test(priority = 4)
    public void deleteSingleStubImposter() {
        MountebankStubApi.deleteImposter("5050", Base.mountebankStubServer).then().statusCode(200);
    }
}
