import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.IDocumentStore;
import net.ravendb.client.documents.conventions.DocumentConventions;
import net.ravendb.client.documents.session.IDocumentSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RavenBD {
    public static void main(String[] args) {
        try (IDocumentStore store = new DocumentStore( new String[]{ "http://localhost:8080" }, "labo")) {
            store.initialize();

        try (IDocumentSession session = store.openSession()) {
            int choice = 0;
            Scanner myInput = new Scanner( System.in );
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            do{
                System.out.println("[1] Add new service visit");
                System.out.println("[2] Get all visits");
                System.out.println("[3] Get service visits by ID");
                System.out.println("[4] Get service visits by car owner");
                System.out.println("[5] Update visit by ID");
                System.out.println("[6] Delete service visits");
                System.out.println("[7] Calculate average sum spent by visit");
                System.out.println("[8] Calculate sum spent by client");
                System.out.println("[9] Quit");
                choice = myInput.nextInt();
                if (choice == 1){
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
                    int rating = myInput.nextInt();
                    CarService add = new CarService();
                    add.setName(name);
                    add.setLastname(lastname);
                    add.setCar(car);
                    add.setDate(date);
                    add.setPrice(price);
                    add.setRating(rating);
                    session.store(add);
                    session.saveChanges();
                    String id_test = add.getId();
                    System.out.println(id_test);
                }
                if (choice == 2){
                    CarService[] result = session
                            .advanced()
                            .loadStartingWith(CarService.class, "CarServices/", null, 0, 128);
                    for( CarService e : result){
                        System.out.println("ID: " + e.getId() +"  "+ e);
                    }
                }
                if (choice == 3){
                    System.out.println("Vist ID to search by:");
                    String id =  reader.readLine();
                    System.out.println(session.load(CarService.class, "CarServices/"+id));

                }
                if (choice == 4){
                    System.out.println("Client name:");
                    String name =  reader.readLine();
                    System.out.println("Client lastname:");
                    String lastname =  reader.readLine();
                    List<CarService> visits = session.query(CarService.class)
                            .whereEquals("lastname", lastname)
                            .whereEquals("name", name)
                            .toList();
                    for(CarService e : visits){
                        System.out.println(e);
                    }
                }
                if (choice == 5){
                    System.out.println("Vist ID to search by:");
                    String id =  reader.readLine();
                    System.out.println(session.load(CarService.class, "CarServices/"+id));
                    if(session.load(CarService.class, "CarServices/"+id) == null){
                        System.out.println("Visit with this ID DOES NOT EXIST");
                    }
                    else{
                        CarService modify = session.load(CarService.class, "CarServices/"+id);
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
                        int rating = myInput.nextInt();
                        modify.setName(name);
                        modify.setLastname(lastname);
                        modify.setCar(car);
                        modify.setDate(date);
                        modify.setPrice(price);
                        modify.setRating(rating);
                        session.saveChanges();
                    }
                }
                if (choice == 6){
                    System.out.println("Vist ID to delete by:");
                    String id =  reader.readLine();
                    session.delete("CarServices/"+id);
                    session.saveChanges();
            }
                if (choice == 7){
                    int sum = 0;
                    int i = 0;
                    CarService[] result = session
                            .advanced()
                            .loadStartingWith(CarService.class, "CarServices/", null, 0, 128);
                    for( CarService e : result){
                        sum += e.getPrice();
                        i++;
                    }
                    System.out.println("Average sum spent: " + sum/i);
                }
                if(choice == 8){
                    int sum = 0;
                    System.out.println("Client name:");
                    String name =  reader.readLine();
                    System.out.println("Client lastname:");
                    String lastname =  reader.readLine();
                    List<CarService> visits = session.query(CarService.class)
                            .whereEquals("lastname", lastname)
                            .whereEquals("name", name)
                            .toList();
                    for(CarService e : visits){
                        sum += e.getPrice();
                    }
                    System.out.println("Sum spent by " + name +" "+ lastname+ ": " + sum);

                }
            }while(choice != 9);
        } catch (IOException e) {
            e.printStackTrace();
            }
        }

    }
}
