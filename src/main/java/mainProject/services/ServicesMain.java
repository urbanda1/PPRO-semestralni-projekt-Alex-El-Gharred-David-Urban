package mainProject.services;

import mainProject.entities.Game;
import mainProject.entities.ItemForSale;
import mainProject.entities.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ServicesMain {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ppro-aegdu");
    public static EntityManager em = emf.createEntityManager();

    private Game game = new Game(0, "RPG", "PC", "Knights of the Old Republic II", "Obsidian Entertainment", "LucasArts", "Star Wars: Knights of the Old Republic II – The Sith Lords is a role-playing video game developed by Obsidian Entertainment and published by LucasArts.");
    private Game game2 = new Game(0, "Strategie", "PC", "Caesar 3", "Impressions Games", "Sierra-Online", "Caesar III is a city-building game released on September 30, 1998, for Microsoft Windows and Mac OS, developed by Impressions Games and published by Sierra On-Line");


    public ServicesMain() {
    }

    //uložit entitu
    public void saveEntity(Object entity) {
        em.getTransaction().begin();

        em.persist(entity);

        em.getTransaction().commit();
    }

    //metoda pro vložení testovacích dat
    public void createInitialGameData() {
        em.getTransaction().begin();
        em.persist(game);
        em.persist(game2);
        em.getTransaction().commit();
    }

    public void createInitialReviewData() {
        em.getTransaction().begin();
        Review review1 = new Review("Trash", "Not very enjoyable.", 50, "24.01.2022", game);
        Review review2 = new Review("Very good", "Childhood game", 80, "24.01.2022", game2);

        em.persist(review1);
        em.persist(review2);

        em.getTransaction().commit();
    }

    public void createInitialItemData() {
        em.getTransaction().begin();
        ItemForSale item1 = new ItemForSale(3, "Knights of the Old republic II", "V dobrém stavu.", game);
        ItemForSale item2 = new ItemForSale(5, "Caesar 3", "S původní krabicí.", game2);

        em.persist(item1);
        em.persist(item2);

        em.getTransaction().commit();
    }
}
