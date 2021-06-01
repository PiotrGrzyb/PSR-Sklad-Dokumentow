public class CarService {
    private String id;
    private String name;
    private String lastname;
    private String car;
    private String date;
    private int price;
    private int rating;
    /*
    public CarService(String id, String name, String lastname, String car, String date, int price, int rating) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.car = car;
        this.date = date;
        this.price = price;
        this.rating = rating;
    }*/

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCar() {
        return car;
    }

    public String getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }
    public String getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setCar(String car) {
        this.car = car;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Client " + name + " " + lastname + " Car: " + car + " Year: " + date + " Price: " + price + " Rating: " + rating;
    }
}
