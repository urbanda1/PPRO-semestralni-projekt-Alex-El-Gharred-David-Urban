package mainProject.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MovieGame {

    @Id
    @GeneratedValue
    private int idMovie;
    private int score;
    private String genre;
    private String platform;
    private String title;
    private String developerCompany;
    private String publisherCompany;
    private String text;

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
