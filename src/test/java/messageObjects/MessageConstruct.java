package messageObjects;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import helpers.PasswordEncryptDecrypt;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;

import base.Base;
import base.BasePage;
import helpers.MyException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class MessageConstruct extends Base{

	public static String getTemplateData(String filter1,String filterValue1,String filter2,String filterValue2,String searchItem) throws FilloException, MyException{
		String template =null;
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(config.properties.get("templateDataPath"));
		String strQuery="Select * from Sheet1 where "+filter1+"='"+filterValue1+"' and " +filter2+ "='"+filterValue2+"'";
		Recordset recordset=connection.executeQuery(strQuery);

		while(recordset.next()){
			template=recordset.getField(searchItem);
		}

		recordset.close();
		connection.close();
		return template;
	}

	public static JsonObject buildJsonObj(JsonObject obj, List<String> buildPath) {
		if(buildPath.size()==0) {return obj;}
		else {
			int count = 0;
			String searchStr = buildPath.get(count);
			buildPath.remove(count);
			obj = buildJsonObj(obj.getAsJsonObject(searchStr),buildPath);
		}
		return obj;
	}

	public static String constructJsonString(String jsonTemplate, String searchString, String modifiedJsonValue) throws ParseException {

		Gson gson = new Gson();
		JsonObject jObj = gson.fromJson(jsonTemplate, JsonObject.class);

		System.out.println("JSON TEMPLATE - "+jObj);

		String[] searchStrList = searchString.split("/");
		List<String> pathList = new ArrayList<String>();

		String modifiedJsonVar = null;
		for(String str:searchStrList) {
			if(str==searchStrList[searchStrList.length-1]) {
				modifiedJsonVar = str;
			}else {
				pathList.add(str);
			}
		}

		if(modifiedJsonVar!=null) {
			buildJsonObj(jObj, pathList).addProperty(modifiedJsonVar, modifiedJsonValue);
		}
		System.out.println("Modified JSON - "+jObj);

		return jObj.toString();
	}


	public static Document convertStringToXML(String xmlTemplate) throws FilloException {
		//Use method to convert XML string content to XML Document object
		Document doc = null;
		//   recordset.moveNext();

		// String xmlStr = recordset.getField("XMLTemplate");

		//Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try
		{
			//Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			//Parse the content to Document object
			doc = builder.parse(new InputSource(new StringReader(xmlTemplate)));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}

	public static String convertDocumentToString(Document doc) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		StringWriter sw = new StringWriter();
		trans.transform(new DOMSource(doc), new StreamResult(sw));
		return sw.toString();
	}


	public static void updateXMLTemplateWithTestData(Document doc, Map<String,String> testData, String fieldName) throws Exception {
				doc.getElementsByTagName(fieldName).item(0).setTextContent(testData.get(fieldName));

		//to Print the updated xml Doc object for unit testing purpose
		Element element = doc.getDocumentElement();

		// get all child nodes
		NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			System.out.println("" + nodes.item(i).getNodeName());
			System.out.println("" + nodes.item(i).getTextContent());
		}

	}



}
