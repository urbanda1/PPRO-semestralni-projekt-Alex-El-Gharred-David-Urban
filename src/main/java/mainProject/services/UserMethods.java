package mainProject.services;

import mainProject.entities.User;
import mainProject.repository.UserRepository;

import javax.persistence.Query;
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

    private void updateUserFromDatabase(User u) {
        em.getTransaction().begin();
        //klasický sql skript query
        Query query = em.createQuery("update User e set e.street=:street, e.fullName=:fullname, e.city=:city, e.zipCode=:zipcode, e.password=:password where e.idUser=:idUser");
        query = query.setParameter("idUser", u.getIdUser());
        query = query.setParameter("street", u.getStreet());
        query = query.setParameter("fullname", u.getFullName());
        query = query.setParameter("city", u.getCity());
        query = query.setParameter("zipcode", u.getZipCode());
        query = query.setParameter("password", u.getPassword());
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public void update(User u) {
        //sql update skript
        updateUserFromDatabase(u);

        //poté změna v seznamu
        int index = 0;
        for (User us : users) {
            if (us.getIdUser() == u.getIdUser()) {
                users.get(index).setStreet(u.getStreet());
                users.get(index).setFullName(u.getFullName());
                users.get(index).setCity(u.getCity());
                users.get(index).setZipCode(u.getZipCode());
                users.get(index).setPassword(u.getPassword());
            }
            index = index + 1;
        }
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
