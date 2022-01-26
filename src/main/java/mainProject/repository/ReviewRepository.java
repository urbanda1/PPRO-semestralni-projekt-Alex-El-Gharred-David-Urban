package mainProject.repository;
import mainProject.entities.Review;

public interface ReviewRepository {

    void delete(Review i);

    void save(Review i);

    void update(Review i);

    Review getReviewById(int id);
}
