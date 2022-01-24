package mainProject.repository;

import mainProject.entities.User;

public interface UserRepository {

    void delete(User i);

    void save(User i);

    User getUserById(int id);

}
