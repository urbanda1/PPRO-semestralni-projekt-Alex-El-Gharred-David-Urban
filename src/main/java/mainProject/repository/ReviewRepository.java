package mainProject.repository;

import mainProject.entities.Review;

import java.util.List;

public interface ReviewRepository {

    void delete(Review i);

    void save(Review i);

    Review getItemById(int id);

    List<Review> getAllReviewsFromUser(int userId);

    List<Review> getAllReviewsByMovie(int movieId);
}
