package mainProject.entities;

import javax.persistence.*;

@Entity
@Table(name = "itemforsale")
public class ItemForSale {

    @Id
    @GeneratedValue
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
    @JoinColumn(name = "idgame")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;


    public ItemForSale(double price, String itemName, String note, Game g) {
        this.price = price;
        this.itemName = itemName;
        this.note = note;
        this.game = g;
//        this.user = u;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


