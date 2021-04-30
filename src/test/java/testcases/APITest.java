package testcases;


import java.lang.reflect.Method;
import java.util.Map;

import listener.MyListener;
import messageObjects.NewAnnotation;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import base.Base;
import dataprovider.MyDataProvider;
import messageObjects.MessageConstruct;
import messageObjects.MessageFactory;
import org.w3c.dom.Document;

import static io.restassured.RestAssured.given;


public class APITest extends Base {
	@Test(enabled = false, dataProvider = "APITestData", dataProviderClass = MyDataProvider.class)
	public void testAPIWithJsonRequest(Map<String,String> testData,Method method) throws Exception
	{
		setTestCase(getParentTestCase().createNode(method.getName()).assignCategory("API"));
		String templateType= testData.get("type");
		String requestType= testData.get("requestType");
		String templateIdValue= testData.get("TemplateId");

		//Get the Template data
		String jsonTemplate = MessageConstruct.getTemplateData("Type",templateType,"TemplateId",templateIdValue,"Template");
		getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO READ THE JSON TEMPLATE - </br>" + jsonTemplate);
		
		//Construct the uri from the Template data sheet
		String uriConstruct = MessageConstruct.getTemplateData("Type",templateType,"TemplateId",templateIdValue,"URIConstruct");
		
		//Construct the JSON String
		String jsonBody = MessageConstruct.constructJsonString(jsonTemplate,testData.get("jsonPath"),testData.get("productID"));
		getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO CONSTRUCT THE JSON BODY - </br>"+jsonBody);
		
		//Post the JSON request
		String postRequestStatus = MessageFactory.getResponse(uri,jsonBody, templateType, requestType, uriConstruct);
		
		//Validate Response
		Assert.assertEquals(postRequestStatus, testData.get("response"), "Request has not been Processed");
		getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO PROCESS POST REQUEST - "+postRequestStatus);
		
		String getRequestStatus = MessageFactory.getResponse(uri,jsonBody, templateType, "GET", uriConstruct);
		Assert.assertEquals(getRequestStatus, testData.get("response"), "Request has not been Processed");
		getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO PROCESS GET REQUEST - "+getRequestStatus);

	}


	@Test(dataProvider = "APITestData", dataProviderClass = MyDataProvider.class)
	@NewAnnotation.TestParameters(testId= "TSP-38")
	public void testAPIWithXMLRequest(Map<String,String> testData,Method method) throws Exception
	{
		setTestCase(getParentTestCase().createNode(method.getName()).assignCategory("API"));
		String templateType= testData.get("type");
		String requestType= testData.get("requestType");
		String templateIdValue= testData.get("TemplateId");

		//Get the Template data
		String xmlTemplate = MessageConstruct.getTemplateData("Type",templateType,"TemplateId",templateIdValue,"Template");
		getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO READ THE XML TEMPLATE - </br>" + xmlTemplate);

		//Convert the XML template(String) to XML Doc Object
		Document doc = MessageConstruct.convertStringToXML(xmlTemplate);

		//update the XML doc(which contains xml template) with the test data read above.
		MessageConstruct.updateXMLTemplateWithTestData(doc,testData,"ev:EventType");

		//Once the XML doc is updated and is ready to be sent as a request body, convert it to String.
		String str1= MessageConstruct.convertDocumentToString(doc);

		//Post the XML request
		String postRequestStatus = MessageFactory.getResponse(xmlUri,str1, templateType, requestType, "");

		//Validate Response
		Assert.assertEquals(postRequestStatus, "200", "Request OK");
		getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO PROCESS POST REQUEST - "+postRequestStatus);

	}


}
