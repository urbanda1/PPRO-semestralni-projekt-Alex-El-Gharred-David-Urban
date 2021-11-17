package mainProject.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="moviegame")
public class MovieGame {

    @Id
    @GeneratedValue
    @Column(name = "idmovie")
    private int idMovie;
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
    @OneToMany(mappedBy = "movieGame")
    private List<Review> reviews = new ArrayList();

    @OneToMany(mappedBy = "movieGame")
    private List<ItemForSale> itemsForSale = new ArrayList();


    public MovieGame(int score, String genre, String platform, String title, String developerCompany, String publisherCompany, String text) {
        this.score = score;
        this.genre = genre;
        this.platform = platform;
        this.title = title;
        this.developerCompany = developerCompany;
        this.publisherCompany = publisherCompany;
        this.text = text;
    }

    public MovieGame() {
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
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



}
