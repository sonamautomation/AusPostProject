package messageObjects;

import static io.restassured.RestAssured.given;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.openqa.selenium.WebDriver;

import base.Base;
import base.BasePage;
import helpers.MyException;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MessageFactory extends Base {

	public static RequestSpecification httpRequest;

	public static String getResponse(String hostURI,String tBody, String templateType, String requestType, String uriConstruct) throws IOException {
		String response = null;
		int statusCode = 0;

		switch (templateType) {
		case "JSON":
			httpRequest = given().auth().none().headers(headerKey, headerValue, "Content-Type", "application/json")
			.body(tBody);
			httpRequest = given().auth().basic(hostUserName, hostPassword)
			.headers(headerKey, headerValue, "Content-Type", "application/json").body(tBody);
			break;

			case "XML":
				if(tBody.contains("soap")){
				statusCode = getSOAPResponse(hostURI,tBody);
				response = String.valueOf(statusCode);
				requestType = "";
				}else{
				httpRequest = given().auth().none().header("Content-Type", "application/xml").body(tBody);
				httpRequest = given().auth().basic(hostUserName, hostPassword).header("Content-Type", "application/xml").body(tBody);
				}
				break;
		}
		switch (requestType) {
		case "POST":
			statusCode = httpRequest.when().post(hostURI + uriConstruct).thenReturn().getStatusCode();
			response = String.valueOf(statusCode);
			break;

		case "GET":
			Response res = httpRequest.when().get(hostURI + uriConstruct);
			System.out.println("GET RESPONSE - " + res.print());
			response = String.valueOf(res.thenReturn().getStatusCode());
		}
		System.out.println("RESPONSE - " + response);

		return response;

	}

	public static int getSOAPResponse(String hostURI,String body) throws IOException {
		//***********SOAP XML works well with HTTPURLConnection Library*************
		String basicAuth = "Basic " + encodedAuth;

		URL url = new URL(hostURI);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("Content-Type", "application/xml; charset=UTF-8");
		conn.setRequestProperty ("Authorization", basicAuth);
		conn.setRequestMethod("POST");
		conn.setReadTimeout(15000);
		conn.setConnectTimeout(15000);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(body);
		writer.flush();
		writer.close();
		os.close();

		return conn.getResponseCode();
	}



}
