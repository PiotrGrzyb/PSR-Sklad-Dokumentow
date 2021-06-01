package pl.kielce.tu.mongodb;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.inc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class TestMongoDB {
	public static void main(String[] args) {

		String user = "student01";
		String password = "student01";
		String host = "localhost";
		int port = 27017;
		String database = "database01";

		String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
		MongoClientURI uri = new MongoClientURI(clientURI);

		MongoClient mongoClient = new MongoClient(uri);

		MongoDatabase db = mongoClient.getDatabase(database);

		db.getCollection("serwis").drop();
		
		MongoCollection<Document> collection = db.getCollection("serwis");
	
		Document test1 = new Document("_id", 1)
				.append("lastname", "Nowak")
                .append("names", "Jan")
                .append("car", "Seat Leon 3")
				.append("date", "01-01-2021")
				.append("price", 5000.0)
				.append("serviceRating", Arrays.asList(new Document("Tires", 5.0), new Document("Suspension", 0.0), new Document("Engine", 4.0),
						new Document("Body", 3.0), new Document("Clutch", 1.0)));
        collection.insertOne(test1);
         
		Document test2 = new Document("_id", 2)
				.append("lastname", "Kowalski")
				.append("names", "Kowal")
				.append("car", "Subaru Impreza 2")
				.append("date", "06-01-2021")
				.append("price", 4999.0)
                .append("serviceRating", new Document("Suspension", 3.5).append("Engine", 5.0).append("Clutch", 3.0));
        collection.insertOne(test2);
		/*
 		List<Document> documents = new ArrayList<Document>();
 		for (int i = 0; i < 2; i++) 
 		    documents.add(new Document("_id", 10 + i));
 		collection.insertMany(documents);
 		 */

 		Document first = collection.find().first();
 		System.out.println("find().first() " + first.toJson());
 		 			
 		for (Document doc : collection.find())
 		    System.out.println("find() " + doc.toJson());
 		
 		Document myDoc = collection.find(lt("_id", 2)).first();
		System.out.println("lt(\"_id\", 2) " + myDoc.toJson());
 		
 		for (Document d : collection.find(or(
 				eq("serviceRating.Engine", 5.0),
 				eq("serviceRating.Clutch", 4.5))))
 		System.out.println("Engine = 5.0 or Clutch = 4.5 " + d.toJson());
		/*
 		for (Document d : collection.find(or(
 				eq("grades", Document.parse("{programming : 5.0}")),
 				eq("grades", Document.parse("{programming : 4.5}")))))
 		System.out.println("or(eq(\"grades\", Document.parse(\"{programming : 5.0}\")),eq(\"grades\", Document.parse(\"{programming : 4.5}\"))) " + d.toJson());
 		
 		for (Document d : collection.find(or(
 				elemMatch("grades", Document.parse("{programming : 5.0}")),
 				elemMatch("grades", Document.parse("{programming : 4.5}")))))
 			System.out.println("find(or(elemMatch(\"grades\", Document.parse(\"{programming : 5.0}\")),elemMatch(\"grades\", Document.parse(\"{programming : 4.5}\"))) " + d.toJson());

 		for (Document d : collection.find(exists("names.1")))
 			System.out.println("find(exists(\"names.1\")) " + d.toJson());
 		*/
 		for (Document d : collection.find(exists("serviceRating.Tires", false)))
 			System.out.println("People who didnt use Tire service " + d.toJson());
 		/*
 		for (Document doc : collection.find().projection(include("firstname", "names")))
 		    System.out.println("find().projection(include(\"firstname\", \"names\")) " + doc.toJson());
 		
 		for (Document doc : collection.find(and(exists("lastname", true), exists("names", true))).projection(include("firstname", "names")))
 		    System.out.println("find(and(exists(\"firstname\", true), exists(\"name\", true))).projection(include(\"firstname\", \"names\")) " + doc.toJson());
 		
 		for (Document doc : collection.find().sort(new Document("_id", -1)))
 		    System.out.println("find().sort(new Document(\"_id\", -1))) " + doc.toJson());
 		
 		for (Document doc : collection.find().sort(new Document("_id", -1)).limit(2))
 		    System.out.println("find().sort(new Document(\"_id\", -1)).limit(2) " + doc.toJson());

		collection.updateOne(eq("_id", 10), new Document("$set", new Document("lastname", "Kowal").append("firstName", "Adam")));
 		for (Document doc : collection.find())
 		    System.out.println("updateOne(eq(\"_id\", 10), new Document(\"$set\", new Document(\"lastname\", \"Kowal\").append(\"firstName\", \"Adam\")) " + doc.toJson());
 		
 		UpdateResult updateResult = collection.updateMany(exists("age"), inc("age", 1));
 		System.out.println(updateResult.getModifiedCount());
 		for (Document doc : collection.find())
 		    System.out.println("updateMany(exists(\"age\"), inc(\"age\", 1)) " + doc.toJson());
 			
		collection.deleteOne(eq("_id", 11));
 		for (Document doc : collection.find())
 		    System.out.println("deleteOne(eq(\"_i\", 11)) " + doc.toJson());		
		*/
		DeleteResult deleteResult = collection.deleteMany(gt("_id", 0));
		System.out.println(deleteResult.getDeletedCount());
 		for (Document doc : collection.find())
 		    System.out.println("deleteMany(gt(\"_id\", 0)) " + doc.toJson());	

		mongoClient.close();
	}
}
