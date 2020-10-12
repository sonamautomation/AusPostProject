package testcases;

import base.Base;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import listener.MyListener;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class MounteBankAPITest extends MyListener {

    @Test(priority=1)
    public void shouldHaveStatusCodeOf200ForStubServerAPI() {
        RestAssured.given().contentType(ContentType.JSON).get(Base.mountebankStubAPI).then().statusCode(200);
    }

    @Test(priority = 2)
    public void createSingleStubImposter() {
        JSONObject requestParameters = new JSONObject();
        requestParameters.put("port","5050")
                .put("protocol","http");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestParameters.toString())
                .post(Base.mountebankStubServer + "imposters")
                .then()
                .statusCode(201);
    }

    @Test(priority = 3)
    public void validateImposter(){
        RestAssured.given().contentType(ContentType.JSON).get(Base.mountebankStubServerDomain + ":5050").then().statusCode(200);

    }

    @Test(priority = 4)
    public void deleteSingleStubImposter() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .delete(Base.mountebankStubServer + "imposters/5050")
                .then()
                .statusCode(200);
    }

}
