package mainProject.entities;

import javax.persistence.*;

@Entity
@Table(name ="itemforsale")
public class ItemForSale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iditemforsale")
    private int idItemForSale;
    @Column(name = "price")
    private double price;
    @Column(name = "itemname")
    private String itemName;
    @Column(name = "note")
    private String note;

    //nastavení cizích klíčů
    @ManyToOne
    @JoinColumn(name = "moviegame")
    private MovieGame movieGame;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;


    public ItemForSale(double price, String itemName, String note) {
        this.price = price;
        this.itemName = itemName;
        this.note = note;
    }

    public ItemForSale() {
    }

    public int getIdItemForSale() {
        return idItemForSale;
    }

    public void setIdItemForSale(int idItemForSale) {
        this.idItemForSale = idItemForSale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
