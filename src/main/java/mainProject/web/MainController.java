package mainProject.web;

import mainProject.entities.Game;
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
    GameMethods mgm = new GameMethods();
    ItemForSaleMethods im = new ItemForSaleMethods();
    ReviewMethods rm = new ReviewMethods();
    UserMethods um = new UserMethods();

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

    @RequestMapping(value = "/formChangeGame/{idgame}", method = { RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView showFormChangeGame(@RequestParam("idgame") int idGame) {

        Game mg = mgm.getGameById(idGame);
        ModelAndView model = new ModelAndView("formChangeGame");
        model.addObject("game", mg);

        return model;
    }

//    @RequestMapping(value = "/game/{idgame}", method = { RequestMethod.POST, RequestMethod.PUT})
//    public ModelAndView showGame(@RequestParam("idgame") int idGame) {
//
//        Game mg = mgm.getGameById(idGame);
//        ModelAndView model = new ModelAndView("game");
//        model.addObject("game", mg);
//
//        return model;
//    }

    @RequestMapping(value = "/game/{idgame}", method = { RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showGame(@PathVariable("idgame") int idGame) {
        Game mg = mgm.getGameById(idGame);
        ModelAndView model = new ModelAndView("game");
        model.addObject("game", mg);
        model.addObject("reviews", rm.getReviews());
        return model;
    }

    @PostMapping("/editGame")
    public String editGame(@ModelAttribute("game") Game g) {

        mgm.update(g);

        return "redirect:/games";
    }


    ///////////////////// recenze ////////////////////////////////
    @RequestMapping("/formCreateReview")
    public ModelAndView showCreateReview() {
        ModelAndView model = new ModelAndView("formCreateReview");
        return model;
    }

    @RequestMapping("/formChangeReview")
    public ModelAndView showFormChangeReview() {
        ModelAndView model = new ModelAndView("formChangeReview");
        return model;
    }

    ///////////////////// recenze ////////////////////////////////
    @PostMapping("/saveUser")
    public String createGame(@ModelAttribute("user") User u) {

        um.save(u);

        return "redirect:/";
    }

}