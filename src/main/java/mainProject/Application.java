package mainProject;

import mainProject.entities.ItemForSale;
import mainProject.entities.MovieGame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        // nějaké prvotní naplnění databáze - zkoušení práce s databází
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ppro-aegdu");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        MovieGame movieGame = new MovieGame(100,"test1","test2","test3","test4","test5","test5");
        em.persist(movieGame);

        em.getTransaction().commit();
        em.close();
        emf.close();

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // namapovat URL / na view jmenem index (tedy pres view-resolver na /WEB-INF/jsp/index.jsp)
        registry.addViewController("/").setViewName("/home.html");
    }

}
