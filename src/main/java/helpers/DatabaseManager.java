package helpers;

import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class DatabaseManager
{
	private static MongoClient mongoClient;
	private static MongoDatabase  dbConn;
	private static MongoCollection<Document> dbCollection;

	public DatabaseManager() 
	{
		try{
			mongoClient = new MongoClient( "localhost" , 27017);
			dbConn= mongoClient.getDatabase("LocatorInformation");
			dbCollection = dbConn.getCollection("LocatorInfo");

		}catch(Exception e){
			System.err.println("Exception: " + e.getMessage());

		}

	}

	public static void createDBRecord(Document doc) {

		dbCollection.insertOne(doc);

	}

	//public static String getDBRecord()
	public static String getDBRecord(String URLValue,String locatorValue,String locatorType)
	{
		String locationAttr="";

		//FindIterable<Document> cur = dbCollection.find(Filters.and());
		//System.out.println("Trying to fetch record with LocatorType " + locatorType + " LocatorValue " + locatorValue + " URL " + URLValue);
		FindIterable<Document> cur = dbCollection.find(Filters.and(Filters.eq("LocatorType", locatorType),
				(Filters.eq("LocatorValue", locatorValue)),(Filters.eq("URL", URLValue))));

		if(cur.iterator().hasNext()) {
			locationAttr = cur.first().getString("LocatorAttributes");
		}
		return locationAttr;
	}

	public static void createOrUpdateDBRecord(String URLValue,String locatorValue,String locatorType,String locatorAttr)
	{
		//dbCollection.findOneAndReplace(filter, doc);
		String timestamp = getCurrentTimeStamp();
		//System.out.println("Trying to fetch record with LocatorType " + locatorType + " LocatorValue " + locatorValue + " URL " + URLValue);
		FindIterable<Document> cur = dbCollection.find(Filters.and(Filters.eq("LocatorType", locatorType),
				(Filters.eq("LocatorValue", locatorValue)),(Filters.eq("URL", URLValue))));
		if(cur.iterator().hasNext()) {
			dbCollection.findOneAndUpdate(Filters.and(Filters.eq("LocatorType", locatorType),
					(Filters.eq("LocatorValue", locatorValue)), (Filters.eq("URL", URLValue))),
					Updates.set("LocatorAttributes", locatorAttr));
		}
		else {
			//System.out.println("Found DB record with LocatorType " + locatorType + " LocatorValue " + locatorValue + " URL " + URLValue);
			Document dbRec = new Document("LocatorType", locatorType)
					.append("LocatorValue", locatorValue)
					.append("URL", URLValue)
					.append("LocatorAttributes", locatorAttr)
					.append("TimeStamp",timestamp);

			dbCollection.insertOne(dbRec);

		}

	}

	public static String getCurrentTimeStamp()
	{
		Date d = new Date();
		String timestamp = d.toString().replace(":", "_").replace(" ", "_");
		return timestamp;
	}






}
