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
        Query query = em.createQuery("update User e set e.street=:street, e.fullName=:fullname, e.city=:city, e.zipCode=:zipcode, e.password=:password where e.idUser=:idUser")
                .setParameter("idUser", u.getIdUser())
                .setParameter("street", u.getStreet())
                .setParameter("fullname", u.getFullName())
                .setParameter("city", u.getCity())
                .setParameter("zipcode", u.getZipCode())
                .setParameter("password", u.getPassword());
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public void update(User u) {
        //sql update skript
        updateUserFromDatabase(u);

        //poté změna v seznamu
        int index = users.indexOf(u);

        users.get(index).setStreet(u.getStreet());
        users.get(index).setFullName(u.getFullName());
        users.get(index).setCity(u.getCity());
        users.get(index).setZipCode(u.getZipCode());
        users.get(index).setPassword(u.getPassword());
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
