package mainProject.repository;
import mainProject.entities.Game;

public interface GameRepository {

    void delete(Game i);

    void save(Game i);

    void update(Game i);

    Game getGameById(int id);
}
