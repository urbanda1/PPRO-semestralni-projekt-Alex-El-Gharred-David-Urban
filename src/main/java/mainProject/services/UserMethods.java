package mainProject.services;

import mainProject.entities.Review;
import mainProject.entities.User;
import mainProject.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserMethods extends ServicesMain implements UserRepository {
    private List<User> users = new ArrayList<>();

    public UserMethods() {
        retriveListFromDatabase();
    }

    //init seznamu
    public void retriveListFromDatabase() {
        users = em.createQuery("select e from User e ",
                User.class).getResultList();
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public void delete(User i) {
        users.remove(i);
    }

    @Override
    public void save(User i) {
        users.add(i);
        saveEntity(i);
    }

    @Override
    public User getUserById(int id) {
        for (User i : users) {
            if (i.getIdUser() == id) {
                return i;
            }
        }
        return null;
    }
}
