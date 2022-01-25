package mainProject.web;

import mainProject.entities.Game;
import mainProject.entities.ItemForSale;
import mainProject.entities.Review;
import mainProject.entities.User;
import mainProject.services.GameMethods;
import mainProject.services.ItemForSaleMethods;
import mainProject.services.ReviewMethods;
import mainProject.services.UserMethods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    private GameMethods mgm = new GameMethods();
    private ItemForSaleMethods im = new ItemForSaleMethods();
    private ReviewMethods rm = new ReviewMethods();
    private UserMethods um = new UserMethods();

    //cizí klíč při vytvoření nové recenze
    int gameID = -1;

    ///////////////////// hlavní formuláře ////////////////////////////////
    @RequestMapping("/games")
    public ModelAndView showGames() {
        ModelAndView model = new ModelAndView("games");
        model.addObject("games", mgm.getGames());

        return model;
    }

    @RequestMapping("/login")
    public ModelAndView showLogin() {
        ModelAndView model = new ModelAndView("login");
        return model;
    }

    //registrace
    @RequestMapping("/register")
    public ModelAndView showRegister() {
        User u = new User();
        ModelAndView model = new ModelAndView("register");
        model.addObject("user", u);
        return model;
    }

    ///////////////////// hry ////////////////////////////////
    @RequestMapping("/formCreateGame")
    public ModelAndView showCreateGame() {
        Game mg = new Game();
        ModelAndView model = new ModelAndView("formCreateGame");
        model.addObject("game", mg);

        return model;
    }

    @RequestMapping("/deleteGame")
    public String deleteGame(@RequestParam("idgame") int idGame) {

        Game game = mgm.getGameById(idGame);
        mgm.delete(game);

        return "redirect:/games";
    }

    @PostMapping("/saveGame")
    public String createGame(@ModelAttribute("game") Game g) {

        mgm.save(g);

        return "redirect:/games";
    }

    @RequestMapping(value = "/formChangeGame/{idgame}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView showFormChangeGame(@RequestParam("idgame") int idGame) {

        Game mg = mgm.getGameById(idGame);
        ModelAndView model = new ModelAndView("formChangeGame");
        model.addObject("game", mg);

        return model;
    }

    @RequestMapping(value = "/game/{idgame}", method = {RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showGame(@PathVariable("idgame") int idGame) {

        Game mg = mgm.getGameById(idGame);
        ModelAndView model = new ModelAndView("game");

        //  vypoctiSkore();
        double score = 0;
        for (Review r : mg.getReviews()) {
            score = score + r.getScore();
        }
        score = score / mg.getReviews().size();

        mg.setScore((int) score);

        model.addObject("game", mg);
        model.addObject("reviews", mg.getReviews());

        return model;
    }

    @PostMapping("/editGame")
    public String editGame(@ModelAttribute("game") Game g) {

        mgm.update(g);

        return "redirect:/games";
    }


    ///////////////////// recenze ////////////////////////////////
    @RequestMapping(value = "/formCreateReview/{idgame}", method = {RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showCreateReview(@PathVariable("idgame") int idGame) {
        Review r = new Review();

        //poslání této hodnoty do formuláře pro vytvoření nové recenze
        gameID = idGame;

        ModelAndView model = new ModelAndView("formCreateReview");
        model.addObject("review", r);

        return model;
    }

    @PostMapping("/saveReview")
    public String saveReview(@ModelAttribute("review") Review r) {

        if (gameID > 0) {
            r.setGame(mgm.getGameById(gameID));
            mgm.getGameById(gameID).getReviews().add(r);
            rm.save(r);
        }

        return "redirect:/games";
    }

    @RequestMapping("/formChangeReview")
    public ModelAndView showFormChangeReview() {
        ModelAndView model = new ModelAndView("formChangeReview");
        return model;
    }

    ///////////////////// uživatel ////////////////////////////////
    @PostMapping("/saveUser")
    public String createGame(@ModelAttribute("user") User u) {

        um.save(u);

        return "redirect:/";
    }

    ///////////////////// položky k prodání ////////////////////////////////
    @RequestMapping(value = "/items/{idgame}", method = {RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showItemsToSell(@PathVariable("idgame") int idGame) {

        Game mg = mgm.getGameById(idGame);
        ModelAndView model = new ModelAndView("items");
        model.addObject("itemsForSale", mg.getItemsForSale());
        model.addObject("game", mg);

        return model;
    }

    @RequestMapping(value = "/formCreateItem/{idgame}", method = {RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showCreateItemForm(@PathVariable("idgame") int idGame) {
        ItemForSale i = new ItemForSale();

        //poslání této hodnoty do formuláře pro vytvoření nové recenze
        gameID = idGame;

        ModelAndView model = new ModelAndView("formCreateItem");
        model.addObject("itemForSale", i);

        return model;
    }

    @PostMapping("/saveItem")
    public String saveReview(@ModelAttribute("itemForSale") ItemForSale i) {

        if (gameID > 0) {
            i.setItemName(mgm.getGameById(gameID).getTitle());
            //provázání na obou stranách vztahů a uložení do databáze
            i.setGame(mgm.getGameById(gameID));
            mgm.getGameById(gameID).getItemsForSale().add(i);
            im.save(i);
        }

        return "redirect:/games";
    }
}