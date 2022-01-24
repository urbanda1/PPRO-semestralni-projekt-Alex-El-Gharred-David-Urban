package mainProject.repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import mainProject.entities.ItemForSale;

import java.util.List;

public interface ItemForSaleRepository<Itemforsale, Integer> {

    void delete(ItemForSale i);

    void save(ItemForSale i);

    ItemForSale getItemById(int id);

    List<ItemForSale> getAllItemsFromUser(int userId);

    List<ItemForSale> getAllItemsByMovie(int movieId);
}
