package mainProject.services;

import mainProject.entities.Game;
import mainProject.repository.GameRepository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameMethods extends ServicesMain implements GameRepository {
    private List<Game> games = new ArrayList<>();

    public GameMethods() {
        retriveListFromDatabase();
    }

    //init seznamu
    public void retriveListFromDatabase() {
        games = em.createQuery("select e from Game e ",
                Game.class).getResultList();
    }

    private void deleteGameFromDatabase(int pkGame) {
        em.getTransaction().begin();
        //klasický sql skript query
        Query query = em.createQuery("delete from Game e where e.idGame=:idgame");
        query = query.setParameter("idgame", pkGame);
        query.executeUpdate();
        em.getTransaction().commit();
    }

    private void updateGameFromDatabase(Game g) {
        em.getTransaction().begin();
        //klasický sql skript query
        Query query = em.createQuery("update Game e set e.developerCompany=:developerCompanyNew, e.publisherCompany=:publisherCompanyNew, e.genre=:genreNew, e.platform=:platformNew, e.text=:textNew, e.title=:titleNew where e.idGame=:idgame");
        query = query.setParameter("idgame", g.getIdGame()).
                setParameter("developerCompanyNew", g.getDeveloperCompany())
                .setParameter("genreNew", g.getGenre())
                .setParameter("platformNew", g.getPlatform())
                .setParameter("publisherCompanyNew", g.getPublisherCompany())
                .setParameter("textNew", g.getText())
                .setParameter("titleNew", g.getTitle());
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public void update(Game g) {
        //sql update skript
        updateGameFromDatabase(g);

        //poté změna v seznamu
        int index = 0;
        for (Game i : games) {
            if (i.getIdGame() == g.getIdGame()) {
                games.get(index).setGenre(g.getGenre());
                games.get(index).setPlatform(g.getPlatform());
                games.get(index).setDeveloperCompany(g.getDeveloperCompany());
                games.get(index).setPublisherCompany(g.getPublisherCompany());
                games.get(index).setTitle(g.getTitle());
                games.get(index).setText(g.getText());
            }
            index = index + 1;
        }
    }

    public List<Game> getGames() {
        return Collections.unmodifiableList(games);
    }

    @Override
    public void delete(Game i) {
        //vymazání z dabáze
        deleteGameFromDatabase(i.getIdGame());

        games.remove(i);
    }

    @Override
    public void save(Game i) {
        //uložení
        games.add(i);
        //uložení do databáze
        saveEntity(i);
    }


    @Override
    public Game getGameById(int id) {
        for (Game i : games) {
            if (i.getIdGame() == id) {
                return i;
            }
        }
        return null;
    }
}
