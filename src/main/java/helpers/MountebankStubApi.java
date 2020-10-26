package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.RestAssured;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MountebankStubApi {

   //Create an Imposter (Stub API)
   public static Response createImposter(String ImposterConfig, String mountebankStubServerURL) throws IOException {
      File StubConfigFile = new File(ImposterConfig);
      String content = new String(Files.readAllBytes(StubConfigFile.toPath()), StandardCharsets.UTF_8);
      return RestAssured.given().contentType(ContentType.JSON)
              .body(content)
              .post(mountebankStubServerURL + "imposters");
   }

   //Delete an Imposter
   public static Response deleteImposter(String imposterPort, String mountebankStubServerURL){
      return RestAssured.given().contentType(ContentType.JSON)
              .delete(mountebankStubServerURL + "imposters/" + imposterPort);
   }

}
