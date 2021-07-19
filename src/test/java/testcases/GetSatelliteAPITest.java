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

public class GetSatelliteAPITest extends TestBase{

	@BeforeClass
	public void getradardetails()
	{
		req=new RequestSpecBuilder().setBaseUri(""+exphost+"").
			     build();
		httprequest=given().spec(req);
		response=new ResponseSpecBuilder().build();
	    responsebody=httprequest.request(Method.GET,""+getSatelliteResource+"");   
		
	}

	@Test(enabled=true)
	public void verifySatelliteResponsecode()
	{
		int actstatuscode=responsebody.getStatusCode();
		Assert.assertEquals(actstatuscode,200);
	}
	
	@Test(enabled=true)
	public void verifySatelliteContenttype()
	{
		String actsatellitecontenttype = responsebody.getContentType();
		System.out.println("The actual satellite response content type " +actsatellitecontenttype);
		Assert.assertEquals(actsatellitecontenttype, expheaderradarcontenttype);
		
	}
	
	@Test(enabled=true)
	public void verifySatelliteresponsexcache()
	{
		String actsatellitexcache=responsebody.getHeader(xcache);
		System.out.println("The actual satellite response X cache is" +actsatellitexcache);
		Assert.assertEquals(actsatellitexcache,expcache);
	}
	
	@Test(enabled=false)
	public void verifySatelliteresponsebody()
	{
		String resbody= responsebody.asString();
		Assert.assertNotNull(resbody);
	}
	
	@Test(enabled=true)
	public void verifySatellitestatusmesg()
	{
		String actsatellitestatusmesg= responsebody.getStatusLine();
		System.out.println("The actual satellite response status is " +actsatellitestatusmesg);
		Assert.assertEquals(actsatellitestatusmesg, expmsg);
	}
	
	@Test(enabled=true)
	public void verifySatelliteresponsecookie()
	{
		Map<String, String> cookie=responsebody.getCookies();
		System.out.println("The cookie for satellite response API is" +cookie);
		Assert.assertTrue(cookie.isEmpty());
	}
	
	@Test(enabled=true)
	public void getSatelliteresponsetime()
	{
		long responsetime=responsebody.getTime();
		System.out.println("The response time for satellite API is " +responsetime);
		Assert.assertNotNull(responsetime);
	}
	
	@Test(enabled=true)
	public void verifySatelliteresponseserver()
	{
		String actresponseserver=responsebody.getHeader(headerserver);
		System.out.println("The actual satellite response server is " +actresponseserver);
		Assert.assertEquals(actresponseserver,expserver);
	}

}
