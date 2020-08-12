package messageObjects;

import base.Base;
import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import com.thed.zephyr.cloud.rest.client.JwtGenerator;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public  class ZephyrJiraMessaging extends MessageConstruct{

    private static String API_ADD_TESTS = "{SERVER}/public/rest/api/1.0/executions/add/cycle/";
    private static String API_GET_EXECUTIONS = "{SERVER}/public/rest/api/1.0/executions/search/cycle/";
    private static String API_UPDATE_EXECUTION = "{SERVER}/public/rest/api/1.0/execution/";
    private static String API_ADD_EXECUTION = "{SERVER}/public/rest/api/1.0/execution";
    private static String API_FIND_PROJ_ID = "{SERVER}/rest/api/3/project/";
    private static String API_CREATE_TEST = "{SERVER}/rest/api/2/issue";
    private static String API_CREATE_TEST_STEP = "{SERVER}/public/rest/api/1.0/teststep/";
    private static String API_SEARCH_ISSUES = "{SERVER}/rest/api/3/search?jql=summary~";
    public static RequestSpecification httpRequest;

    private static ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, accountId)
            .build();

    public static void main(String[] args) throws JSONException, ParseException{
	         // String projId = getProjectID();
            //  updateResultsToZephyr(testCaseId);
    }

    public static void updateResultsToZephyr(String TestcaseName, String testCaseId, int status, int row) throws URISyntaxException, IOException {
        Map<String, String> executionId;
        String projId = getProjectID();

           //If test case not found then search by Jira JQL, i.e based on Test case summary
            if(testCaseId == null) {
                testCaseId = getIssueByJQL(TestcaseName);
            }

            // If test case still not found then create a new testcase
            if(testCaseId == null){
            testCaseId = createTestCaseWithTestSteps(projId,TestcaseName);
              }

            //Add tests to execution cycle
            callAddTestsToCycle(testCaseId, projId);

            // Get the execution id for an un executed test case instance in the execution cycle
            executionId = callGetExecutionsByCycleId(testCaseId, projId);

            //Update the test case status
            callUpdateExecutions(executionId, status, row, projId);
    }

    /*
     * Add test case execution to CycleId
     */
    private static void callAddTestsToCycle(String testCaseId,String projectId) throws URISyntaxException {
        final String addTestsUri = API_ADD_TESTS.replace("{SERVER}", zephyrBaseUrl) + cycleId;

        // Create JSON object by providing input values
        String[] IssueIds = new String[1];
        IssueIds[0] = testCaseId;
        JSONObject addTestsObj = new JSONObject();
        addTestsObj.put("issues", IssueIds);
        addTestsObj.put("method", 1);
        addTestsObj.put("projectId", projectId);
        addTestsObj.put("versionId", versionId);
        addTestsObj.put("cycleId", cycleId);

        StringEntity addTestsJSON = null;
        try {
            addTestsJSON = new StringEntity(addTestsObj.toString());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        addTestsToCycle(addTestsUri, client, accessKey, addTestsJSON);
    }


    /**
     * Get Execution Id's by CycleId
     */
    private static Map<String,String> callGetExecutionsByCycleId(String testCaseId,String projectId) throws URISyntaxException {
        Map<String, String> executionIds;
        final String getExecutionsUri = API_GET_EXECUTIONS.replace("{SERVER}", zephyrBaseUrl) + cycleId + "?projectId="
                + projectId + "&versionId=" + versionId;

        executionIds = getExecutionsByCycleId(getExecutionsUri, client, accessKey,testCaseId);
        return executionIds;
    }

    /**
     * Update Executions with Status by Execution Id
     */
    private static void callUpdateExecutions(Map<String, String> executionIds, int status, int row, String projectId) throws IOException, URISyntaxException {

        // Create JSON object by providing input values
        JSONObject statusObj = new JSONObject();
        statusObj.put("id", status);

        JSONObject executeTestsObj = new JSONObject();
        executeTestsObj.put("status", statusObj);
        executeTestsObj.put("cycleId", cycleId);
        executeTestsObj.put("projectId", projectId);
        executeTestsObj.put("versionId", versionId);
        executeTestsObj.put("comment", "Test data row "+ row + " executed by ZAPI Cloud");

        for (String key : executionIds.keySet()) {
            final String updateExecutionUri = API_UPDATE_EXECUTION.replace("{SERVER}", zephyrBaseUrl) + key;
            executeTestsObj.put("issueId", executionIds.get(key));
            StringEntity executeTestsJSON = null;
            try {
                executeTestsJSON = new StringEntity(executeTestsObj.toString());
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            updateExecutions(updateExecutionUri, client, accessKey, executeTestsJSON);
        }

    }

    private static String getIssueByJQL(String testCaseSummary) throws JSONException {
            String issueSearchUri = API_SEARCH_ISSUES.replace("{SERVER}", jiraBaseURL) + testCaseSummary;
            byte[] bytesEncoded = Base64.encodeBase64((jiraUserName + ":" + apiToken).getBytes());
            String authorizationHeader = "Basic " + new String(bytesEncoded);
            Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, authorizationHeader);

            String issueId = null;
            HttpResponse response = null;
            //HttpClient restClient = new DefaultHttpClient();
            HttpClient restClient = HttpClientBuilder.create().build();
            try {
                HttpGet createProjectReq = new HttpGet(issueSearchUri);
                createProjectReq.addHeader(header);
                createProjectReq.addHeader("Content-Type", "application/json");


                response = restClient.execute(createProjectReq);
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert response != null;
            int statusCode = response.getStatusLine().getStatusCode();
            // System.out.println(statusCode);

            //Validate response and find test case id in the response
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity entity1 = response.getEntity();
                String string1 = null;
                try {
                    string1 = EntityUtils.toString(entity1);
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }

                System.out.println(string1);
                assert string1 != null;
                JSONObject allIssues = new JSONObject(string1);
                JSONArray IssuesArray = allIssues.getJSONArray("issues");
                if (IssuesArray.length() == 0) {
                    return null;
                }

                for (int j = 0; j < IssuesArray.length() ; j++) {
                    JSONObject jobj = IssuesArray.getJSONObject(j);
                    issueId = jobj.getString("key");
                }
            }
            return issueId;
        }

        private static void addTestsToCycle(String uriStr, ZFJCloudRestClient client, String accessKey,
                                           StringEntity addTestsJSON) throws URISyntaxException, JSONException {

            URI uri = new URI(uriStr);
            int expirationInSec = 360;
            JwtGenerator jwtGenerator = client.getJwtGenerator();
            String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);

            HttpResponse response = null;
            HttpClient restClient = HttpClientBuilder.create().build();

            HttpPost addTestsReq = new HttpPost(uri);
            addTestsReq.addHeader("Content-Type", "application/json");
            addTestsReq.addHeader("Authorization", jwt);
            addTestsReq.addHeader("zapiAccessKey", accessKey);
            addTestsReq.setEntity(addTestsJSON);

            try {
                response = restClient.execute(addTestsReq);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Validate response
            assert response != null;
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                System.out.println("Successfully added Testcase Instance to the execution cycle");
            } else {
                try {
                    throw new ClientProtocolException("Unexpected response status: " + statusCode);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                }
            }
        }

        private static Map<String, String> getExecutionsByCycleId(String uriStr, ZFJCloudRestClient client,
                                                                  String accessKey, String testCaseId) throws URISyntaxException, JSONException {
            Map<String, String> executionIds = new HashMap<>();
            URI uri = new URI(uriStr);
            int expirationInSec = 360;
            JwtGenerator jwtGenerator = client.getJwtGenerator();
            String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);

            HttpResponse response = null;
            HttpClient restClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("Authorization", jwt);
            httpGet.setHeader("zapiAccessKey", accessKey);

            try {
                response = restClient.execute(httpGet);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            assert response != null;
            int statusCode = response.getStatusLine().getStatusCode();
            // System.out.println(statusCode);
            //Validate response
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity entity1 = response.getEntity();
                String string1 = null;
                try {
                    string1 = EntityUtils.toString(entity1);
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }

                assert string1 != null;
                JSONObject allIssues = new JSONObject(string1);
                JSONArray IssuesArray = allIssues.getJSONArray("searchObjectList");
                // System.out.println(IssuesArray.length());
                if (IssuesArray.length() == 0) {
                    return executionIds;
                }
                for (int j = 0; j <= IssuesArray.length() - 1; j++) {
                    JSONObject jobj = IssuesArray.getJSONObject(j);
                    if (jobj.getString("issueKey").contains(testCaseId)){
                        JSONObject jobj2 = jobj.getJSONObject("execution");
                        JSONObject statusResponseObj = jobj2.getJSONObject("status");
                        if(statusResponseObj.getInt("id") == -1){
                            String executionId = jobj2.getString("id");
                            long IssueId = jobj2.getLong("issueId");
                            executionIds.put(executionId, String.valueOf(IssueId));
                            break;
                        }

                    }
                }
            }
            return executionIds;
        }

        private static void updateExecutions(String uriStr, ZFJCloudRestClient client, String accessKey,
                                              StringEntity executionJSON) throws URISyntaxException, JSONException, ParseException, IOException {

            URI uri = new URI(uriStr);
            int expirationInSec = 360;
            JwtGenerator jwtGenerator = client.getJwtGenerator();
            String jwt = jwtGenerator.generateJWT("PUT", uri, expirationInSec);

            HttpResponse response = null;
            HttpClient restClient = HttpClientBuilder.create().build();

            HttpPut executeTest = new HttpPut(uri);
            executeTest.addHeader("Content-Type", "application/json");
            executeTest.addHeader("Authorization", jwt);
            executeTest.addHeader("zapiAccessKey", accessKey);
            executeTest.setEntity(executionJSON);

            try {
                response = restClient.execute(executeTest);
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert response != null;
            int statusCode = response.getStatusLine().getStatusCode();
            String executionStatus = "No Test Executed";
            HttpEntity entity = response.getEntity();
            //Validate response
            if (statusCode >= 200 && statusCode < 300) {
                String string;
                try {
                    string = EntityUtils.toString(entity);
                    JSONObject executionResponseObj = new JSONObject(string);
                    JSONObject descriptionResponseObj = executionResponseObj.getJSONObject("execution");
                    JSONObject statusResponseObj = descriptionResponseObj.getJSONObject("status");
                    executionStatus = statusResponseObj.getString("description");
                    System.out.println(executionResponseObj.get("issueKey") + "--" + executionStatus);
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }

            } else {

                try {
                    String string;
                    string = EntityUtils.toString(entity);
                    JSONObject executionResponseObj = new JSONObject(string);
                    cycleId = executionResponseObj.getString("clientMessage");
                    throw new ClientProtocolException("Unexpected response status: " + statusCode);

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                }
            }
        }

    /*
     * Find project Id based on project key
     */
       private static String getProjectID(){
        final String findProjID = API_FIND_PROJ_ID.replace("{SERVER}", jiraBaseURL) + projectKey;
        byte[] bytesEncoded = Base64.encodeBase64((jiraUserName + ":" + apiToken).getBytes());
        String authorizationHeader = "Basic " + new String(bytesEncoded);
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, authorizationHeader);

        /* httpRequest = given().auth().none().headers(headerKey, headerValue, "Content-Type", "application/json");
           httpRequest = given().headers("Authorization", authorizationHeader);//.body(tBody);
           Response res = httpRequest.when().get(findProjID);
          String response1 = String.valueOf(res.thenReturn().getStatusCode());

          //Validate Response
           Assert.assertEquals(response1, "200", "Request has not been Processed");

           JsonPath jsonPathEvaluator = res.jsonPath();
           String ProjId = jsonPathEvaluator.get("id");
           System.out.println(ProjId);*/

        HttpResponse response = null;
        HttpClient restClient = HttpClientBuilder.create().build();
        try
        {
            HttpGet createTestReq = new HttpGet(findProjID);
            createTestReq.addHeader(header);
            createTestReq.addHeader("Content-Type", "application/json");
            response = restClient.execute(createTestReq);
        } catch(IOException e){
            e.printStackTrace();
        }

        String projectId = null;
        assert response != null;
           //Validate response
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity1 = response.getEntity();
        if(statusCode >=200&&statusCode< 300){
            String string1 = null;
            try {
                string1 = EntityUtils.toString(entity1);
                System.out.println(string1);
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }

            assert string1 != null;
            JSONObject createTestResp = new JSONObject(string1);
            projectId = createTestResp.getString("id");
            System.out.println("testId :" + projectId);
        } else
        {
            try {
                String string;
                string = EntityUtils.toString(entity1);
                JSONObject executionResponseObj = new JSONObject(string);
                System.out.println(executionResponseObj.toString());
                throw new ClientProtocolException("Unexpected response status: " + statusCode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return projectId;
    }

    /*
     * Add new test case in Jira
     */
    private static String createTestCaseWithTestSteps(String projectId, String testCaseName) throws URISyntaxException {
         final String createTestUri = API_CREATE_TEST.replace("{SERVER}", jiraBaseURL);
        final String createTestStepUri = API_CREATE_TEST_STEP.replace("{SERVER}", zephyrBaseUrl);

        /*
         * Create Test Parameters, declare Create Test Issue fields Declare more
         * field objects if required
         */

        JSONObject projectObj = new JSONObject();
        projectObj.put("id", projectId); // Project ID where the Test to be Created

        JSONObject issueTypeObj = new JSONObject();
        issueTypeObj.put("id", issueTypeId); // IssueType ID which is Test issue type

        /*
         * Create JSON payload to POST. Add more field objects if required
         *
         *
         */

        JSONObject fieldsObj = new JSONObject();
        fieldsObj.put("project", projectObj);
        fieldsObj.put("summary", testCaseName);
        fieldsObj.put("issuetype", issueTypeObj);
        // fieldsObj.put("assignee", assigneeObj);
        // fieldsObj.put("reporter", reporterObj);

        JSONObject createTestObj = new JSONObject();
        createTestObj.put("fields", fieldsObj);
        System.out.println(createTestObj.toString());

        byte[] bytesEncoded = Base64.encodeBase64((jiraUserName + ":" + apiToken).getBytes());
        String authorizationHeader = "Basic " + new String(bytesEncoded);
        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, authorizationHeader);

        StringEntity createTestJSON = null;
        try {
            createTestJSON = new StringEntity(createTestObj.toString());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        HttpResponse response = null;

        HttpClient restClient = HttpClientBuilder.create().build();
        try {
            // System.out.println(issueSearchURL);
            HttpPost createTestReq = new HttpPost(createTestUri);
            createTestReq.addHeader(header);
            createTestReq.addHeader("Content-Type", "application/json");
            createTestReq.setEntity(createTestJSON);

            response = restClient.execute(createTestReq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String testId = null;
        String issueKey = null;
        assert response != null;
        int statusCode = response.getStatusLine().getStatusCode();
        //Validate response
        HttpEntity entity1 = response.getEntity();
        if (statusCode >= 200 && statusCode < 300) {

            String string1 = null;
            try {
                string1 = EntityUtils.toString(entity1);
                System.out.println(string1);
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }

            assert string1 != null;
            JSONObject createTestResp = new JSONObject(string1);
            testId = createTestResp.getString("id");
           issueKey = createTestResp.getString("key");
            System.out.println("testId :" + testId);
        } else {

            try {
                String string;
                string = EntityUtils.toString(entity1);
                JSONObject executionResponseObj = new JSONObject(string);
                System.out.println(executionResponseObj.toString());
                throw new ClientProtocolException("Unexpected response status: " + statusCode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* Create test Steps ***/

        /*
         * Create Steps Replace the step,data,result values as required
         */

        JSONObject testStepJsonObj = new JSONObject();
        testStepJsonObj.put("step", "Test Step 1");
        testStepJsonObj.put("data", "Test Data 1");
        testStepJsonObj.put("result", "Expected Result 1");

        StringEntity createTestStepJSON = null;
        try {
            createTestStepJSON = new StringEntity(testStepJsonObj.toString());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String finalURL = createTestStepUri + testId + "?projectId=" + projectId;
        URI uri = new URI(finalURL);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);
        System.out.println(uri.toString());
        System.out.println(jwt);

        HttpResponse responseTestStep = null;

        HttpPost addTestStepReq = new HttpPost(uri);
        addTestStepReq.addHeader("Content-Type", "application/json");
        addTestStepReq.addHeader("Authorization", jwt);
        addTestStepReq.addHeader("zapiAccessKey", accessKey);
        addTestStepReq.setEntity(createTestStepJSON);

        try {
            responseTestStep = restClient.execute(addTestStepReq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert responseTestStep != null;
        int testStepStatusCode = responseTestStep.getStatusLine().getStatusCode();
        System.out.println(testStepStatusCode);
        System.out.println(response.toString());
        //Validate response
        if (statusCode >= 200 && statusCode < 300) {
            HttpEntity entity = responseTestStep.getEntity();
            String string;
            String stepId;
            try {
                string = EntityUtils.toString(entity);
                JSONObject testStepObj = new JSONObject(string);
                stepId = testStepObj.getString("id");
                System.out.println("stepId :" + stepId);
                System.out.println(testStepObj.toString());
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                throw new ClientProtocolException("Unexpected response status: " + statusCode);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        }
        return issueKey;
    }

    private static String AddExecutionForTestCase(String uriStr, ZFJCloudRestClient client, String accessKey,
                                                  StringEntity executionJSON) throws URISyntaxException, JSONException, ParseException, IOException {

        URI uri = new URI(uriStr);
        int expirationInSec = 360;
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);
        // System.out.println(uri.toString());
        // System.out.println(jwt);

        HttpResponse response = null;
        //HttpClient restClient = new DefaultHttpClient();
        HttpClient restClient = HttpClientBuilder.create().build();

        HttpPost executeTest = new HttpPost(uri);
        executeTest.addHeader("Content-Type", "application/json");
        executeTest.addHeader("Authorization", jwt);
        executeTest.addHeader("zapiAccessKey", accessKey);
        executeTest.setEntity(executionJSON);

        try {
            response = restClient.execute(executeTest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int statusCode = response.getStatusLine().getStatusCode();
        // System.out.println(statusCode);
        String idResponse = "";
        // System.out.println(response.toString());
        HttpEntity entity = response.getEntity();

        if (statusCode >= 200 && statusCode < 300) {
            String string;
            try {
                string = EntityUtils.toString(entity);
                JSONObject executionResponseObj = new JSONObject(string);
                JSONObject idResponseObj = executionResponseObj.getJSONObject("execution");
                idResponse = idResponseObj.getString("id");
                System.out.println(executionResponseObj.get("issueKey") + "--" + idResponse);
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }

        } else {

            try {
                String string;
                string = EntityUtils.toString(entity);
                JSONObject executionResponseObj = new JSONObject(string);
                cycleId = executionResponseObj.getString("clientMessage");
                // System.out.println(executionResponseObj.toString());
                throw new ClientProtocolException("Unexpected response status: " + statusCode);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        }
        return idResponse;
    }
}


