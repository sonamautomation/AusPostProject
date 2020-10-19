package testcases;

import com.aventstack.extentreports.Status;
import dataprovider.MyDataProvider;
import listener.CustomListener;
import messageObjects.MessageConstruct;
import messageObjects.MessageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Map;

public class MounteBankAPITest extends CustomListener {

    //Pre-requisite: Mountebank stub server is running and accessible

     @Test(dataProvider = "APITestData",dataProviderClass = MyDataProvider.class,priority = 1)
    public void testMountebankAPI(Map<String,String> testData, Method method) throws Exception
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

        String getRequestStatus = MessageFactory.getResponse(uri,jsonBody, templateType, "POST", uriConstruct);
        Assert.assertEquals(getRequestStatus, testData.get("response"), "Request has not been Processed");
        getTestCase().log(Status.INFO, "SUCCESSFULLY ABLE TO PROCESS GET REQUEST - "+getRequestStatus);

    }

}
