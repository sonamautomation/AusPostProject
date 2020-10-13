package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import io.restassured.RestAssured;

public class MountebankStubApi {

   public static Response createImposter(String ImposterConfig, String mountebankStubServerURL){
      return (Response) RestAssured.given().contentType(ContentType.JSON)
              .body(ImposterConfig)
              .post(mountebankStubServerURL + "imposters");
   }

   public static Response deleteImposter(String imposterPort, String mountebankStubServerURL){
      return (Response) RestAssured.given().contentType(ContentType.JSON)
              .delete(mountebankStubServerURL + "imposters/" + imposterPort);
   }

}
