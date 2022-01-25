package mainProject.entities;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "idreview")
    private int idReview;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @Column(name = "score")
    private int score;

    @Column(name = "date")
    private String date;

    //nastavení cizích klíčů
    @ManyToOne
    @JoinColumn(name = "idgame")
    private Game game;

    //nastavení cizích klíčů
    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;

    public Review(String title, String text, int score, String date, Game g) {
        this.title = title;
        this.text = text;
        this.score = score;
        this.date = date;
        this.game = g;
//        this.user = u;
    }

    public Review() {
    }

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
