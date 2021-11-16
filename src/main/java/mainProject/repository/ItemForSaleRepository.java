package mainProject.repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import mainProject.entities.ItemForSale;
import org.springframework.data.repository.CrudRepository;

public interface ItemForSaleRepository<ItemForSale, Integer> {

    void odstran(mainProject.entities.ItemForSale i);

    void save(mainProject.entities.ItemForSale i);

}
//    public List<MainMethods> getInzeraty();
//
//    public void pridej(MainMethods i);
//
//    public void odstran(MainMethods i);
//
//    public void odstran(int id);
//
//    public void uprav(MainMethods i);
//
//    public MainMethods getById(int id);
//
//    public List<MainMethods> getInzeratyByKategorie(String kategorie);
