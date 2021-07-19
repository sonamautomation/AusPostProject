package testcases;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.TestBase;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Method;

public class GetRadarPastDataAPITest extends TestBase {
 
	@BeforeClass
	public void getradardetails()
	{
		req=new RequestSpecBuilder().setBaseUri(""+exphost+"").
			     build();
		httprequest=given().spec(req);
		response=new ResponseSpecBuilder().build();
	    responsebody=httprequest.request(Method.GET,""+getradarpastdataresource+"");   
		
	}

	@Test(enabled=true)
	public void verifyRadarPastResponsecode()
	{
		int actstatuscode=responsebody.getStatusCode();
		Assert.assertEquals(actstatuscode,200);
	}
	
	@Test(enabled=true)
	public void verifyRadarPastContenttype()
	{
		String actradarcontenttype = responsebody.getContentType();
		System.out.println("The actual radar response content type " +actradarcontenttype);
		Assert.assertEquals(actradarcontenttype, expheaderradarcontenttype);
		
	}
	
	@Test(enabled=true)
	public void verifyradarpastresponsebody()
	{
		String resbody= responsebody.asString();
		Assert.assertNotNull(resbody);
	}
	
	@Test(enabled=true)
	public void verifyradarpaststatusmesg()
	{
		String actradarstatusmesg= responsebody.getStatusLine();
		System.out.println("The actual radar status is " +actradarstatusmesg);
		Assert.assertEquals(actradarstatusmesg, expmsg);
	}
	
	@Test(enabled=true)
	public void verifyradarpastresponsecookie()
	{
		Map<String, String> cookie=responsebody.getCookies();
		System.out.println("The cookie for Radar response API is" +cookie);
		Assert.assertTrue(cookie.isEmpty());
	}
	
	@Test(enabled=true)
	public void getRadarpastresponsetime()
	{
		long responsetime=responsebody.getTime();
		System.out.println("The response time for Radar API is " +responsetime);
		Assert.assertNotNull(responsetime);
	}
	
	@Test(enabled=true)
	public void verifyradarpastresponseserver()
	{
		String actresponseserver=responsebody.getHeader(headerserver);
		System.out.println("The actual radar response server is " +actresponseserver);
		Assert.assertEquals(actresponseserver,expserver);
	}
}


