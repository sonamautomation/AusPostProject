package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Method;
import pages.WeatherDetails;

import static io.restassured.RestAssured.given;

import java.util.Map;

public class GetWeatherDetailsTest extends TestBase{
	
	
	
	@BeforeClass
	public void getweatherdetails()
	{
		
		RestAssured.baseURI = TestBase.baseUri;
		req=new RequestSpecBuilder().setBaseUri(RestAssured.baseURI).
			     build();
		httprequest= given().spec(req);
	    response=new ResponseSpecBuilder().build();
	    responsebody=httprequest.request(Method.GET,""+getResource+"");
	    weatherdetail= new WeatherDetails();
	    weatherdetail=httprequest.when().get(""+getResource+"").then().spec(response).extract().as(pages.WeatherDetails.class);
	
	   
	}
	
	@Test(enabled=true)
	public void verifyResponseBody()
	{
		
		weatherdetail=httprequest.when().get(""+getResource+"").then().spec(response).extract().as(pages.WeatherDetails.class);
		String acthost= weatherdetail.getHost();
		System.out.println("The actual host is "+acthost);
		Assert.assertEquals(acthost,exphost);
		
	}
	
	@Test(enabled=true)
	public void verifyResponseVersion()
	{
		weatherdetail=httprequest.when().get(""+getResource+"").then().spec(response).extract().as(pages.WeatherDetails.class);
		float actversion=weatherdetail.getVersion();
		System.out.println("The actual version is " +actversion);
	    Assert.assertEquals(actversion, expversion);
	}
	
	@Test(enabled=true)
	public void verifyresponsenowcast()
	{
		weatherdetail=httprequest.when().get(""+getResource+"").then().spec(response).extract().as(pages.WeatherDetails.class);
		int acttime =weatherdetail.getRadar().getNowcast().get(0).getTime();
		System.out.println("The actual time is "+acttime);
		String actpath= weatherdetail.getRadar().getNowcast().get(0).getPath();
		System.out.println("The actual path is "+actpath);
		Assert.assertNotNull(acttime);	 
		Assert.assertNotNull(actpath);
	}

	@Test(enabled=true)
	public void verifyStatuscode()
	{
		int actstatuscode= responsebody.getStatusCode();
		System.out.println("The status code is " + actstatuscode);
		Assert.assertEquals(actstatuscode, expcode);
		
	}
	
	@Test(enabled=true)
	public void verifyResponseServer()
	{
		String actServer=responsebody.getHeader(headerserver);
		System.out.println("The response value of server is "+actServer);
		Assert.assertEquals(actServer, expserver);
	}
	
   @Test(enabled=true)
   public void verifyResponseContenttype()
   {
	   String actcontenttype= responsebody.getContentType();
	   System.out.println("The response value of ETag is "+actcontenttype);
	   Assert.assertEquals(actcontenttype,expcontenttype);
   }
	
   @Test(enabled=true)
   public void verifyCookie()
   {
	   Map<String, String> actcookie=responsebody.getCookies();
	   System.out.println("The cookie is empty " +actcookie);
	   Assert.assertTrue(actcookie.isEmpty());
	   
   }
   
   @Test(enabled=true)
   public void verifyresponsemsg()
   {
	   String actresponsemesg = responsebody.getStatusLine();
	   System.out.println("The actual status response message is " +actresponsemesg);
	   Assert.assertEquals(actresponsemesg, expmsg);
   }
	
   @Test(enabled=true)
   public void verifyresponsetime()
   {
	   long responsetime=responsebody.getTime();
	   System.out.println("The response time is " +responsetime);
	   Assert.assertTrue(responsetime>50);
	
   }
   
   @Test(enabled=true)
   public void verifycontentencoding()
   {
	   String actcontentencoding = responsebody.getHeader(actencoding);
	   System.out.println("The content encoding value is "+actcontentencoding);
	   Assert.assertEquals(actcontentencoding, expencoding );
   }
   
 
}







