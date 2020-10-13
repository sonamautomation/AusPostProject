package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.RestAssured;

public class MountebankStubApi {

   //Create an Imposter (Stub API)
   public static Response createImposter(String ImposterConfig, String mountebankStubServerURL){
      return RestAssured.given().contentType(ContentType.JSON)
              .body(ImposterConfig)
              .post(mountebankStubServerURL + "imposters");
   }

   //Delete an Imposter
   public static Response deleteImposter(String imposterPort, String mountebankStubServerURL){
      return RestAssured.given().contentType(ContentType.JSON)
              .delete(mountebankStubServerURL + "imposters/" + imposterPort);
   }

}
