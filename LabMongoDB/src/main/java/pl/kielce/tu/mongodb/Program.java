package pl.kielce.tu.mongodb;
import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.mongodb.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.conversions.Bson;

public class Program {
    public static <JSONObject> void main(String[] args) throws IOException {
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
                .append("date", "2021")
                .append("price", 5000.0)
                .append("serviceRating", Arrays.asList(new Document("Tires", 5.0), new Document("Suspension", 0.0), new Document("Engine", 4.0),
                        new Document("Body", 3.0), new Document("Clutch", 1.0)));
        collection.insertOne(test1);

        Document test2 = new Document("_id", 2)
                .append("lastname", "Kowalski")
                .append("names", "Kowal")
                .append("car", "Subaru Impreza 2")
                .append("date", "2021")
                .append("price", 4999.0)
                .append("serviceRating", new Document("Suspension", 3.5).append("Engine", 5.0).append("Clutch", 3.0));
        collection.insertOne(test2);

        int choice = 0;

        Scanner myInput = new Scanner( System.in );
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        do{
            System.out.println("[1] Add new service visit");
            System.out.println("[2] Get all visits");
            System.out.println("[3] Get service visits by ID");
            System.out.println("[4] Get service visits by car owner");
            System.out.println("[5] Update visit by ID");
            System.out.println("[6] Delete service visits");
            System.out.println("[7] Calculate average sum spent by visit in years");
            System.out.println("[8] Quit");
            choice = myInput.nextInt();
            if (choice == 1){
                System.out.println("ID:");
                int id = myInput.nextInt();
                System.out.println("Client name:");
                String name =  reader.readLine();
                System.out.println("Client lastname:");
                String lastname =  reader.readLine();
                System.out.println("Car:");
                String car =  reader.readLine();
                System.out.println("Date:");
                String date =  reader.readLine();
                System.out.println("Visit price:");
                int price = myInput.nextInt();
                System.out.println("Tires service rating:");
                int tiresRating = myInput.nextInt();
                System.out.println("Suspension service rating:");
                int suspensionRating = myInput.nextInt();
                System.out.println("Engine service rating:");
                int engineRating = myInput.nextInt();
                System.out.println("Clutch service rating:");
                int clutchRating = myInput.nextInt();
                System.out.println("Body service rating:");
                int bodyRating = myInput.nextInt();
                Document document = new Document("_id", id)
                        .append("lastname", lastname)
                        .append("names", name)
                        .append("car", car)
                        .append("date", date)
                        .append("price", price)
                        .append("serviceRating", new Document("Suspension", suspensionRating).append("Engine", engineRating)
                                .append("Clutch", clutchRating).append("Tires", tiresRating).append("Body", bodyRating));
                collection.insertOne(document);
            }
            if (choice == 2){
                System.out.println("Wizity w serwisie");
                for (Document doc : collection.find())
                    System.out.println(doc.toJson());
                System.out.println("");
            }
            if (choice == 3){
                System.out.println("Visit ID:");
                int id = myInput.nextInt();
                for (Document d : collection.find(
                        eq("_id", id)))
                    System.out.println(d.toJson());
            }
            if (choice == 4){
                System.out.println("Client name:");
                String name =  reader.readLine();
                System.out.println("Client lastname:");
                String lastname =  reader.readLine();
                for (Document d : collection.find(and(
                        eq("names", name),
                        eq("lastname", lastname))))
                    System.out.println(d.toJson());
            }
            if (choice == 5){
                System.out.println("ID:");
                int id = myInput.nextInt();
                int count = 0;
                for (Document d : collection.find(
                        eq("_id", id))) count++;
                if(count == 0)
                    System.out.println("Wizity o tym ID NIE MA W SERWISIE");
                else{
                System.out.println("");
                System.out.println("Client name:");
                String name =  reader.readLine();
                System.out.println("Client lastname:");
                String lastname =  reader.readLine();
                System.out.println("Car:");
                String car =  reader.readLine();
                System.out.println("Date:");
                String date =  reader.readLine();
                System.out.println("Visit price:");
                int price = myInput.nextInt();
                System.out.println("Tires service rating:");
                int tiresRating = myInput.nextInt();
                System.out.println("Suspension service rating:");
                int suspensionRating = myInput.nextInt();
                System.out.println("Engine service rating:");
                int engineRating = myInput.nextInt();
                System.out.println("Clutch service rating:");
                int clutchRating = myInput.nextInt();
                System.out.println("Body service rating:");
                int bodyRating = myInput.nextInt();
                collection.updateOne(eq("_id", id), new Document("$set", new Document("lastname", lastname)
                        .append("names", name)
                        .append("car", car)
                        .append("date", date)
                        .append("price", price)
                        .append("serviceRating", new Document("Suspension", suspensionRating).append("Engine", engineRating)
                                .append("Clutch", clutchRating).append("Tires", tiresRating).append("Body", bodyRating))));
                }
            }
            if (choice == 6){
                System.out.println("Delete by ID:");
                int id = myInput.nextInt();
                int count = 0;
                for (Document d : collection.find(
                        eq("_id", id))) count++;
                if(count == 0)
                    System.out.println("Wizity o tym ID NIE MA W SERWISIE");
                else
                collection.deleteOne(eq("_id", id));
            }
            if (choice == 7){
                Bson group = group("$date", avg("avgPrice", "$price"));
                Bson sort = sort(ascending("avgPrice"));
                Bson limit = limit(1000);
                List<Document> results = collection.aggregate(Arrays.asList(group,sort,limit)).into(new ArrayList<>());
                for (Document doc : results)
                    System.out.println(doc.toJson());
            }
        }while(choice != 8);

        mongoClient.close();
    }
}
