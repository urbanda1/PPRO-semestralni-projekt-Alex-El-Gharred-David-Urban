package mainProject.services;

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
    private void retriveListFromDatabase() {
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
    public Review getItemById(int id) {
        for (Review i : reviews) {
            if (i.getIdReview() == id) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Review> getAllReviewsFromUser(int userId) {
//        for (ItemForSale i : itemsForSale) {
//            if (i.getIdItemForSale() == id) {
//        return null;
//    }
        return null;
    }

    @Override
    public List<Review> getAllReviewsByMovie(int movieId) {
        return null;
    }
}
