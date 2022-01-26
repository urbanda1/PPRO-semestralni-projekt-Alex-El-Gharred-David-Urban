package mainProject.repository;
import mainProject.entities.ItemForSale;

public interface ItemForSaleRepository {

    void delete(ItemForSale i);

    void save(ItemForSale i);

    void update(ItemForSale i);

    ItemForSale getItemById(int id);
}
