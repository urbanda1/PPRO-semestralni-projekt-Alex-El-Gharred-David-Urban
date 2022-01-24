package mainProject.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue
    @Column(name = "idgame")
    private int idGame;
    @Column(name = "score")
    private int score;
    @Column(name = "genre")
    private String genre;
    @Column(name = "platform")
    private String platform;
    @Column(name = "title")
    private String title;
    @Column(name = "developercompany")
    private String developerCompany;
    @Column(name = "publishercompany")
    private String publisherCompany;
    @Column(name = "text")
    private String text;

    //nastavení cizích klíčů
    @OneToMany(mappedBy = "game")
    private List<Review> reviews = new ArrayList();

    @OneToMany(mappedBy = "game")
    private List<ItemForSale> itemsForSale = new ArrayList();


    public Game(int score, String genre, String platform, String title, String developerCompany, String publisherCompany, String text) {
        this.score = score;
        this.genre = genre;
        this.platform = platform;
        this.title = title;
        this.developerCompany = developerCompany;
        this.publisherCompany = publisherCompany;
        this.text = text;
    }

    public Game() {
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int IdGame) {
        this.idGame = IdGame;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeveloperCompany() {
        return developerCompany;
    }

    public void setDeveloperCompany(String developerCompany) {
        this.developerCompany = developerCompany;
    }

    public String getPublisherCompany() {
        return publisherCompany;
    }

    public void setPublisherCompany(String publisherCompany) {
        this.publisherCompany = publisherCompany;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
    
}
