package app;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Flats")
public class Flat {
    @Id
    @GeneratedValue
    Integer id;

    String street;
    String city;
    Integer price;
    Integer squares;
    Integer roomsCount;

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                ", squares=" + squares +
                ", roomsCount=" + roomsCount +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setSquares(Integer squares) {
        this.squares = squares;
    }

    public void setRoomsCount(Integer roomsCount) {
        this.roomsCount = roomsCount;
    }

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getPrice() {
        return price;
    }

    public int getSquares() {
        return squares;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public Flat() {
    }

    public Flat(String street, String city, Integer price, Integer squares, Integer roomsCount) {
        this.street = street;
        this.city = city;
        this.price = price;
        this.squares = squares;
        this.roomsCount = roomsCount;
    }
}
