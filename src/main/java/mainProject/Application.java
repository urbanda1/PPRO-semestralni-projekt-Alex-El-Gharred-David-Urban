package mainProject;

import mainProject.entities.Game;
import mainProject.entities.ItemForSale;
import mainProject.entities.Review;
import mainProject.services.ServicesMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@SpringBootApplication
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        //naplnění počátečními daty
        ServicesMain sm = new ServicesMain();

        //vložení nějakých dat do databáze, když žádné nejsou
        try {
            List<Game> games;
            games = ServicesMain.em.createQuery("select e from Game e ",
                    Game.class).getResultList();
            System.out.println(games.get(0).getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            sm.createInitialGameData();
        }

        try {
            List<Review> reviews;
            reviews = ServicesMain.em.createQuery("select e from Review e ",
                    Review.class).getResultList();
            System.out.println(reviews.get(0).getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            sm.createInitialReviewData();
        }

        try {
            List<ItemForSale> items;
            items = ServicesMain.em.createQuery("select e from ItemForSale e ",
                    ItemForSale.class).getResultList();
            System.out.println(items.get(0).getItemName());
        } catch (Exception e) {
            e.printStackTrace();
            sm.createInitialItemData();
        }

        SpringApplication.run(Application.class, args);
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // namapovat URL / na view jmenem index (tedy pres view-resolver na /WEB-INF/jsp/index.jsp)
        registry.addViewController("/").setViewName("/home.html");
    }

}
