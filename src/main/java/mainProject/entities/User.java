package mainProject.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "iduser")
    private int idUser;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "zipcode")
    private int zipCode;
    @Column(name = "fullname")
    private String fullName;

    //nastavení cizích klíčů
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList();

    @OneToMany(mappedBy = "user")
    private List<ItemForSale> itemsForSale = new ArrayList();

    public User(String username, String email, String password, String city, String street, int zipCode, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.fullName = fullName;
    }

    public User() {
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<ItemForSale> getItemsForSale() {
        return itemsForSale;
    }

    public void setItemsForSale(List<ItemForSale> itemsForSale) {
        this.itemsForSale = itemsForSale;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
