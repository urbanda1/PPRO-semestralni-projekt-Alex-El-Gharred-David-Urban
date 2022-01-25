package mainProject.services;

import mainProject.entities.Game;

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
}
