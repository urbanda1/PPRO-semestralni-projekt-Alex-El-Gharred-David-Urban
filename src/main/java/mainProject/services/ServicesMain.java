package mainProject.services;

import mainProject.entities.Game;
import mainProject.entities.Review;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ServicesMain {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ppro-aegdu");
    public static EntityManager em = emf.createEntityManager();

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
        Game game = new Game(0, "RPG", "PC", "Knights of the Old Republic II", "Obsidian Entertainment", "LucasArts", "Star Wars: Knights of the Old Republic II – The Sith Lords is a role-playing video game developed by Obsidian Entertainment and published by LucasArts.");
        Game game2 = new Game(0, "Strategie", "PC", "Caesar 3", "Impressions Games", "Sierra-Online", "Caesar III is a city-building game released on September 30, 1998, for Microsoft Windows and Mac OS, developed by Impressions Games and published by Sierra On-Line");
        em.persist(game);
        em.persist(game2);
        em.getTransaction().commit();
    }

    public void createInitialReviewData() {
        em.getTransaction().begin();
        Review review1 = new Review("Trash", "Not very enjoyable.", 50, "24.01.2022");
        Review review2 = new Review("Very good", "Childhood game", 80, "24.01.2022");
        em.persist(review1);
        em.persist(review2);

        em.getTransaction().commit();
    }





}
