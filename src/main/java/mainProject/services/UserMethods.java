package mainProject.services;

import mainProject.entities.User;
import mainProject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserMethods extends ServicesMain implements UserRepository {
    private List<User> users = new ArrayList<>();

    public UserMethods() {
        retriveListFromDatabase();
    }

    //init seznamu
    private void retriveListFromDatabase() {
        users = em.createQuery("select e from User e ",
                User.class).getResultList();
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
