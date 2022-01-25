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

import java.util.List;

@Controller
public class MainController {
    public GameMethods mgm = new GameMethods();
    public ItemForSaleMethods im = new ItemForSaleMethods();
    public ReviewMethods rm = new ReviewMethods();
    public UserMethods um = new UserMethods();

    //chybná zpráva při vytváření/modifikace uživatele
    private String warningMessageUser;

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

        //ted je potřeba vymazat všechny záznam recenzí a položek k prodeji, které jsou spojeny s hrou přes foreign key
        List<ItemForSale> itemsToDelete = game.getItemsForSale();
        List<Review> reviewsToDelete = game.getReviews();

        for (Review r : reviewsToDelete) {
            rm.delete(r);
        }

        for (ItemForSale i : itemsToDelete) {
            im.delete(i);
        }

        mgm.delete(game);

        return "redirect:/games";
    }

    @PostMapping("/saveGame")
    public ModelAndView createGame(@ModelAttribute("game") Game g) {
        boolean gameValidated;

        //validace hry
        gameValidated = validateGame(g);

        if (gameValidated) {
            mgm.save(g);
            return showGames();
        } else {
            return showErrorMessage("Popis a název hry musí být vyplněn");
        }
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

        // vypocti celkové hodnocení
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
    public ModelAndView editGame(@ModelAttribute("game") Game g) {
        boolean gameValidated;

        gameValidated = validateGame(g);

        if (gameValidated) {
            mgm.update(g);
            return showGames();
        } else {
            return showErrorMessage("Popis a název hry musí být vyplněn");
        }
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
    public ModelAndView saveReview(@ModelAttribute("review") Review r) {

        boolean reviewValidated;

        //validace recenze
        reviewValidated = validateReview(r);

        if (reviewValidated) {
            if (gameID > 0) {
                r.setGame(mgm.getGameById(gameID));
                mgm.getGameById(gameID).getReviews().add(r);
                rm.save(r);
            }
            return showGame(gameID);
        } else {
            return showErrorMessage("Skóre a datum napsání recenze musí být vyplněno");
        }
    }

    @RequestMapping("/formChangeReview")
    public ModelAndView showFormChangeReview() {
        ModelAndView model = new ModelAndView("formChangeReview");
        return model;
    }

    ///////////////////// uživatel ////////////////////////////////
    @PostMapping("/saveUser")
    public ModelAndView saveUser(@ModelAttribute("user") User u) {

        boolean userValidated;

        userValidated = validateUser(u);

        if (userValidated) {
            //validace uživatele
            um.save(u);
            return showGames();
        } else {
            return showErrorMessage(warningMessageUser);
        }
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
    public ModelAndView saveReview(@ModelAttribute("itemForSale") ItemForSale i) {
        boolean itemValidated;

        //validace položky
        itemValidated = validateItem(i);

        if (itemValidated) {
            if (gameID > 0) {
                i.setItemName(mgm.getGameById(gameID).getTitle());
                //provázání na obou stranách vztahů a uložení do databáze
                i.setGame(mgm.getGameById(gameID));
                mgm.getGameById(gameID).getItemsForSale().add(i);
                im.save(i);
            }
            return showGame(gameID);
        } else {
            return showErrorMessage("Cena a popis položky k prodání musí být vyplněn");
        }
    }

    //////////////// Varovné zprávy se budou zobrazovat přes tuto stránku //////////////////////
    @RequestMapping("/errorPage")
    public ModelAndView showErrorMessage(String warningMessage) {

        //poslání této hodnoty do formuláře pro vytvoření nové recenze

        ModelAndView model = new ModelAndView("errorPage");
        model.addObject("warningMessage", warningMessage);

        return model;
    }

    //////////////// VALIDACE DAT //////////////////////
    //validace hry
    private boolean validateGame(Game g) {
        //zde si pouze oveřuji, aby něco bylo vyplněno v textu a v titlu
        if ((g.getTitle() != "") && (g.getText() != "")) {
            return true;
        } else {
            return false;
        }
    }

    //validace recenze
    private boolean validateReview(Review r) {
        //zde si pouze oveřuji, aby něco bylo vyplněno v textu a v titlu
        if ((r.getDate() != "") && (r.getScore() > -1)) {
            return true;
        } else {
            return false;
        }
    }

    //validace položky
    private boolean validateItem(ItemForSale i) {
        //zde si pouze oveřuji, aby něco bylo vyplněno v textu a v titlu
        if ((i.getNote() != "") && (i.getPrice() > 0)) {
            return true;
        } else {
            return false;
        }
    }

    //validace uživatele
    private boolean validateUser(User u) {
        boolean check = true;

        //validace délky hesla
        if (u.getPassword().length() < 8) {
            warningMessageUser = "Heslo musí mít velikost alespoň 8 znaků";
            check = false;
        }

        //validace, aby všechna data byla vyplněna
        if ((u.getPassword().equals("")) || (u.getUsername().equals("")) || (u.getEmail().equals(""))) {
            warningMessageUser = "Některé povinné údaje je potřeba doplnit";
            check = false;
        }

        //validace hesla
        if ((u.getPassword().equals("12345678")) || (u.getPassword().equals("heslo"))) {
            warningMessageUser = "Špatně zabezpečené heslo";
            check = false;
        }

        //validace mailu
        for (User user : um.getUsers()) {
            //pokud tento mail již v databáze je, tak to vyhodí chybu
            if (user.getEmail().equals(u.getEmail())) {
                warningMessageUser = "Tento mail již v databázi existuje";
                check = false;
            }
        }

        //validace jména
        for (User user : um.getUsers()) {
            //pokud tento mail již v databáze je, tak to vyhodí chybu
            if (user.getUsername().equals(u.getUsername())) {
                warningMessageUser = "Toto uživatelské jméno má už jiný uživatel";
                check = false;
            }
        }

        if (check) {
            return true;
        } else {
            return false;
        }
    }
}