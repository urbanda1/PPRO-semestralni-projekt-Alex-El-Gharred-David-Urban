package mainProject.services;


import mainProject.entities.ItemForSale;
import mainProject.repository.ItemForSaleRepository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ItemForSaleMethods extends ServicesMain implements ItemForSaleRepository {
    private List<ItemForSale> itemsForSale = new ArrayList<>();

    public ItemForSaleMethods() {
        retriveListFromDatabase();
    }

    //init seznamu
    private void retriveListFromDatabase() {
        itemsForSale = em.createQuery("select e from ItemForSale e ",
                ItemForSale.class).getResultList();
    }

    private void deleteItemFromDatabase(int pkItem) {
        em.getTransaction().begin();
        //klasický sql skript query
        Query query = em.createQuery("delete from ItemForSale e where e.idItemForSale=:iditemforsale");
        query = query.setParameter("iditemforsale", pkItem);
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public void delete(ItemForSale i) {
        //vymazání z dabáze
        deleteItemFromDatabase(i.getIdItemForSale());

        itemsForSale.remove(i);
    }

    @Override
    public void save(ItemForSale i) {
        itemsForSale.add(i);
        saveEntity(i);
    }

    @Override
    public ItemForSale getItemById(int id) {
        for (ItemForSale i : itemsForSale) {
            if (i.getIdItemForSale() == id) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<ItemForSale> getAllItemsFromUser(int userId) {
//        for (ItemForSale i : itemsForSale) {
//            if (i.getIdItemForSale() == id) {
//        return null;
//    }
        return null;
    }

    @Override
    public List<ItemForSale> getAllItemsByMovie(int movieId) {
        return null;
    }
}
