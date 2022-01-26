package mainProject.repository;
import mainProject.entities.User;

public interface UserRepository {

    void delete(User i);

    void save(User i);

    void update(User u);

    User getUserById(int id);

}
