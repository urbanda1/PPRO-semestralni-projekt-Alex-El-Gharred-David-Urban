package mainProject.entities;

import javax.persistence.*;

@Entity
public class ItemForSale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idItemForSale;
    private double price;
    private String itemName;
    private String note;

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
