package mainProject.services;

import mainProject.entities.Game;
import mainProject.entities.Review;
import mainProject.repository.ReviewRepository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReviewMethods extends ServicesMain implements ReviewRepository {
    private List<Review> reviews = new ArrayList<>();

    public ReviewMethods() {
        retriveListFromDatabase();
    }

    //init seznamu
    public void retriveListFromDatabase() {
        reviews = em.createQuery("select e from Review e ",
                Review.class).getResultList();
    }

    private void deleteReviewFromDatabase(int pkReview) {
        em.getTransaction().begin();
        //klasický sql skript query
        Query query = em.createQuery("delete from Review e where e.idReview=:idreview");
        query = query.setParameter("idreview", pkReview);
        query.executeUpdate();
        em.getTransaction().commit();
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    private void updateReviewFromDatabase(Review r) {
        em.getTransaction().begin();
        //klasický sql skript query
        Query query = em.createQuery("update Review e set e.title=:title, e.text=:text, e.score=:score, e.date=:date where e.idReview=:idReview");
        query = query.setParameter("idReview", r.getIdReview())
                .setParameter("title", r.getTitle())
                .setParameter("score", r.getScore())
                .setParameter("date", r.getDate())
                .setParameter("text", r.getText());
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public void update(Review r) {
        //sql update skript
        updateReviewFromDatabase(r);

        //poté změna v seznamu
        int index = 0;
        for (Review t : reviews) {
            if (t.getIdReview() == t.getIdReview()) {
                reviews.get(index).setDate(r.getDate());
                reviews.get(index).setText(r.getText());
                reviews.get(index).setTitle(r.getTitle());
                reviews.get(index).setScore(r.getScore());
            }
            index = index + 1;
        }
    }

    @Override
    public void delete(Review i) {
        deleteReviewFromDatabase(i.getIdReview());
        reviews.remove(i);
    }

    @Override
    public void save(Review i) {
        reviews.add(i);
        saveEntity(i);
    }

    @Override
    public Review getReviewById(int id) {
        for (Review i : reviews) {
            if (i.getIdReview() == id) {
                return i;
            }
        }
        return null;
    }
}
