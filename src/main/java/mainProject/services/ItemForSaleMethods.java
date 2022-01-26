package mainProject.services;

import mainProject.entities.ItemForSale;
import mainProject.repository.ItemForSaleRepository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemForSaleMethods extends ServicesMain implements ItemForSaleRepository {
    private List<ItemForSale> itemsForSale = new ArrayList<>();

    public ItemForSaleMethods() {
        retriveListFromDatabase();
    }

    //init seznamu
    public void retriveListFromDatabase() {
        itemsForSale = em.createQuery("select e from ItemForSale e ",
                ItemForSale.class).getResultList();
    }

    public void deleteItemFromDatabase(int pkItem) {
        em.getTransaction().begin();
        //klasický sql skript query
        Query query = em.createQuery("delete from ItemForSale e where e.idItemForSale=:iditemforsale");
        query = query.setParameter("iditemforsale", pkItem);
        query.executeUpdate();
        em.getTransaction().commit();
    }

    private void updateItemFromDatabase(ItemForSale i) {
        em.getTransaction().begin();
        //klasický sql skript query
        Query query = em.createQuery("update ItemForSale e set e.note=:note, e.price=:price where e.idItemForSale=:idItemForSale");
        query = query.setParameter("idItemForSale", i.getIdItemForSale())
                .setParameter("note", i.getNote())
                .setParameter("price", i.getPrice());
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public void update(ItemForSale i) {
        //sql update skript
        updateItemFromDatabase(i);

        //poté změna v seznamu
        int index = 0;
        for (ItemForSale it : itemsForSale) {
            if (it.getIdItemForSale() == i.getIdItemForSale()) {
                itemsForSale.get(index).setNote(i.getNote());
                itemsForSale.get(index).setPrice(i.getPrice());
            }
            index = index + 1;
        }
    }

    @Override
    public void delete(ItemForSale i) {
        //vymazání z dabáze
        deleteItemFromDatabase(i.getIdItemForSale());

        itemsForSale.remove(i);
    }

    public List<ItemForSale> getItems() {
        return Collections.unmodifiableList(itemsForSale);
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

}
