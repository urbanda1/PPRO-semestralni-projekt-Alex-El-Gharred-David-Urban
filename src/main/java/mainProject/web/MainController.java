package mainProject.web;

import mainProject.entities.Game;
import mainProject.entities.ItemForSale;
import mainProject.entities.Review;
import mainProject.entities.User;
import mainProject.services.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Controller
public class MainController {
    private final GameMethods mgm = new GameMethods();
    private final ItemForSaleMethods im = new ItemForSaleMethods();
    private final ReviewMethods rm = new ReviewMethods();
    private final UserMethods um = new UserMethods();

    //cookies a ověření přihlášení
    private boolean userLoggedIn = false;
    private Cookie cookie;
    private int cookieIdOfLoggedInUser = 0;

    //pro hašování hesla
    BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    //chybná zpráva při vytváření/modifikace uživatele
    private String warningMessageUser;

    //cizí klíč při vytvoření nové recenze
    int gameID = -1;

    ///////////////////// hlavní formuláře ////////////////////////////////
    @RequestMapping("/games")
    public ModelAndView showGames(HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        ModelAndView model = new ModelAndView("games");
        model.addObject("games", mgm.getGames());

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);

        return model;
    }

    @RequestMapping("/")
    public ModelAndView showHome(HttpServletRequest request, boolean loggedIn) {

        if (!loggedIn) {
            //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                        .map(Cookie::getValue)
                        .collect(Collectors.joining(", ")));
                userLoggedIn = true;
            } else {
                userLoggedIn = false;
            }
        }

        ModelAndView model = new ModelAndView("home");

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    //přihlášení formulář
    @RequestMapping("/login")
    public ModelAndView showLogin(HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        User u = new User();
        ModelAndView model = new ModelAndView("login");
        model.addObject("loginData", u);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    //přihlášení formulář
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        if (userLoggedIn) {
            cookie = new Cookie("user-id", null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    //přihlášení uživatele
    @RequestMapping("/loginUser")
    public ModelAndView loginUser(@ModelAttribute("loginData") User u, HttpServletResponse response, HttpServletRequest request) {
        boolean userExists = false;
        int idUser;
        User userFromDatabase = new User();

        //zkontroluj přihl. údaje
        //ověř nejdříve jestli uživatel existuje
        for (User us : um.getUsers()) {
            if (us.getEmail().equals(u.getEmail())) {
                userExists = true;
                userFromDatabase = us;
            }
        }

        idUser = userFromDatabase.getIdUser();

        //ověření hesla z databáze a hesla, které uživatel vloží do přihl. formuláře
        boolean isPasswordMatched = bcryptEncoder.matches(u.getPassword(), userFromDatabase.getPassword());

        // zkontroluj heslo
        if (userExists) {
            if (isPasswordMatched) {

                //uživatel se přihlásí a uloží se mu cookies do prohlížeče
                cookie = new Cookie("user-id", String.valueOf(idUser));
                cookie.setMaxAge(60 * 60);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);

                response.addCookie(cookie);

                //pomocí této boolenovské hodnoty se uživateli bude ověřovat, jestli je přihlášen nebo ne
                userLoggedIn = true;

                return showHome(request, true);

            } else {
                return showErrorMessage("Špatné heslo", request);
            }
        } else {
            return showErrorMessage("Tento uživatel neexistuje", request);
        }
    }

    //registrace
    @RequestMapping("/register")
    public ModelAndView showRegister(HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        User u = new User();
        ModelAndView model = new ModelAndView("register");
        model.addObject("user", u);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    ///////////////////// hry ////////////////////////////////
    @RequestMapping("/formCreateGame")
    public ModelAndView showCreateGame(HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        Game mg = new Game();
        ModelAndView model = new ModelAndView("formCreateGame");
        model.addObject("game", mg);


        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
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
    public ModelAndView createGame(@ModelAttribute("game") Game g, HttpServletRequest request) {
        boolean gameValidated;

        //validace hry
        gameValidated = validateGame(g);

        if (gameValidated) {
            mgm.save(g);
            return showGames(request);
        } else {
            return showErrorMessage("Popis a název hry musí být vyplněn", request);
        }
    }

    @RequestMapping(value = "/formChangeGame/{idgame}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView showFormChangeGame(@RequestParam("idgame") int idGame, HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        Game mg = mgm.getGameById(idGame);
        ModelAndView model = new ModelAndView("formChangeGame");
        model.addObject("game", mg);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    @RequestMapping(value = "/game/{idgame}", method = {RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showGame(@PathVariable("idgame") int idGame, HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

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

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    @PostMapping("/editGame")
    public ModelAndView editGame(@ModelAttribute("game") Game g, HttpServletRequest request) {
        boolean gameValidated;

        gameValidated = validateGame(g);

        if (gameValidated) {
            mgm.update(g);
            return showGames(request);
        } else {
            return showErrorMessage("Popis a název hry musí být vyplněn", request);
        }
    }

    ///////////////////// recenze ////////////////////////////////
    @RequestMapping(value = "/formCreateReview/{idgame}", method = {RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showCreateReview(@PathVariable("idgame") int idGame, HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        Review r = new Review();

        //poslání této hodnoty do formuláře pro vytvoření nové recenze
        gameID = idGame;

        ModelAndView model = new ModelAndView("formCreateReview");
        model.addObject("review", r);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    @RequestMapping(value = "/formChangeReview/{idgame}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView showFormChangeReview(@RequestParam("idReview") int idReview, HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        Review r = rm.getReviewById(idReview);
        ModelAndView model = new ModelAndView("formChangeReview");
        model.addObject("review", r);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    @PostMapping("/editReview")
    public ModelAndView editGame(@ModelAttribute("review") Review r, HttpServletRequest request) {
        boolean reviewValidated;

        reviewValidated = validateReview(r);

        if (reviewValidated) {
            rm.update(r);
            return showUser(request);
        } else {
            return showErrorMessage("Recenze musí obsahovat skóre a datum vytvoření/úpravy", request);
        }
    }

    @PostMapping("/deleteReview")
    public String deleteReview(@RequestParam("idReview") int idReview) {

        Review review = rm.getReviewById(idReview);

        //smazání položky
        mgm.getGameById(review.getGame().getIdGame()).getReviews().remove(review);
        um.getUserById(cookieIdOfLoggedInUser).getReviews().remove(review);
        rm.delete(review);

        return "redirect:/user";
    }

    @PostMapping("/saveReview")
    public ModelAndView saveReview(@ModelAttribute("review") Review r, HttpServletRequest request) {

        boolean reviewValidated;

        //validace recenze
        reviewValidated = validateReview(r);

        if (reviewValidated) {
            if (gameID > 0) {
                r.setUser(um.getUserById(cookieIdOfLoggedInUser));
                um.getUserById(cookieIdOfLoggedInUser).getReviews().add(r);

                r.setGame(mgm.getGameById(gameID));
                mgm.getGameById(gameID).getReviews().add(r);
                rm.save(r);
            }
            return showGame(gameID, request);
        } else {
            return showErrorMessage("Skóre a datum napsání recenze musí být vyplněno", request);
        }
    }

    @RequestMapping("/formChangeReview")
    public ModelAndView showFormChangeReview(HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        ModelAndView model = new ModelAndView("formChangeReview");

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    ///////////////////// uživatel ////////////////////////////////
    @RequestMapping(value = "/user")
    public ModelAndView showUser(HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        User u = um.getUserById(cookieIdOfLoggedInUser);

        ModelAndView model = new ModelAndView("user");
        model.addObject("reviews", u.getReviews());
        model.addObject("itemsForSale", u.getItemsForSale());

        model.addObject("currentUser", cookieIdOfLoggedInUser);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    @RequestMapping(value = "/formChangeUser")
    public ModelAndView showFormChangeUser(HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        User u = um.getUserById(cookieIdOfLoggedInUser);
        ModelAndView model = new ModelAndView("formChangeUser");
        model.addObject("user", u);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    @PostMapping("/editUser")
    public ModelAndView editUser(@ModelAttribute("user") User u, HttpServletRequest request) {
        boolean userValidated;

        userValidated = validateOldUser(u);

        String encryptedPassword = bcryptEncoder.encode(u.getPassword());

        if (userValidated) {
            u.setPassword(encryptedPassword);
            um.update(u);
            return showUser(request);
        } else {
            return showErrorMessage("Chyba při změně údaju - pravděpodobně špatná forma hesla", request);
        }
    }


    @PostMapping("/saveUser")
    public ModelAndView saveUser(@ModelAttribute("user") User u, HttpServletRequest request) {
        boolean userValidated;

        //zahashovaní hesla před vložením do databáze
        String encryptedPassword = bcryptEncoder.encode(u.getPassword());

        System.out.println(encryptedPassword);

        userValidated = validateUser(u);

        if (userValidated) {
            //validace uživatele
            u.setPassword(encryptedPassword);
            um.save(u);
            return showGames(request);
        } else {
            return showErrorMessage(warningMessageUser, request);
        }
    }

    ///////////////////// položky k prodání ////////////////////////////////
    @RequestMapping(value = "/items/{idgame}", method = {RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showItemsToSell(@PathVariable("idgame") int idGame, HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        Game mg = mgm.getGameById(idGame);
        ModelAndView model = new ModelAndView("items");
        model.addObject("itemsForSale", mg.getItemsForSale());
        model.addObject("game", mg);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);

        return model;
    }

    @RequestMapping(value = "/formChangeItem/{idItemForSale}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView showFormChangeItem(@RequestParam("idItemForSale") int idItemForSale, HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        ItemForSale i = im.getItemById(idItemForSale);
        ModelAndView model = new ModelAndView("formChangeItem");
        model.addObject("itemForSale", i);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    @PostMapping("/editItemForSale")
    public ModelAndView editGame(@ModelAttribute("itemForSale") ItemForSale i, HttpServletRequest request) {
        boolean itemValidated;

        itemValidated = validateItem(i);

        if (itemValidated) {
            im.update(i);
            return showUser(request);
        } else {
            return showErrorMessage("Položka musí obsahovat popis a cenu", request);
        }
    }

    @RequestMapping(value = "/formCreateItem/{idgame}", method = {RequestMethod.GET, RequestMethod.PUT})
    public ModelAndView showCreateItemForm(@PathVariable("idgame") int idGame, HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        ItemForSale i = new ItemForSale();

        //poslání této hodnoty do formuláře pro vytvoření nové recenze
        gameID = idGame;

        ModelAndView model = new ModelAndView("formCreateItem");
        model.addObject("itemForSale", i);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);
        return model;
    }

    @PostMapping("/saveItem")
    public ModelAndView saveReview(@ModelAttribute("itemForSale") ItemForSale i, HttpServletRequest request) {
        boolean itemValidated;

        //validace položky
        itemValidated = validateItem(i);

        if (itemValidated) {
            if (gameID > 0) {
                i.setUser(um.getUserById(cookieIdOfLoggedInUser));
                um.getUserById(cookieIdOfLoggedInUser).getItemsForSale().add(i);

                i.setItemName(mgm.getGameById(gameID).getTitle());
                //provázání na obou stranách vztahů a uložení do databáze
                i.setGame(mgm.getGameById(gameID));
                mgm.getGameById(gameID).getItemsForSale().add(i);
                im.save(i);
            }
            return showGame(gameID, request);
        } else {
            return showErrorMessage("Cena a popis položky k prodání musí být vyplněn", request);
        }
    }

    @PostMapping("/deleteItemForSale")
    public String deleteItem(@RequestParam("idItemForSale") int idItemForSale) {

        ItemForSale item = im.getItemById(idItemForSale);

        //smazání položky
        mgm.getGameById(item.getGame().getIdGame()).getItemsForSale().remove(item);
        um.getUserById(cookieIdOfLoggedInUser).getItemsForSale().remove(item);
        im.delete(item);

        return "redirect:/user";
    }

    //////////////// Varovné zprávy se budou zobrazovat přes tuto stránku //////////////////////
    @RequestMapping("/errorPage")
    public ModelAndView showErrorMessage(String warningMessage, HttpServletRequest request) {
        //ověření jestli je uživatel přihlášen - jestli má cookies pořád v prohlížeči
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieIdOfLoggedInUser = parseInt(Arrays.stream(cookies)
                    .map(Cookie::getValue)
                    .collect(Collectors.joining(", ")));
            userLoggedIn = true;
        } else {
            userLoggedIn = false;
        }

        //poslání této hodnoty do formuláře pro vytvoření nové recenze

        ModelAndView model = new ModelAndView("errorPage");
        model.addObject("warningMessage", warningMessage);

        //poslání přihlášení uživatele do frontendu
        model.addObject("userLoggedIn", userLoggedIn);

        return model;
    }

    //////////////// VALIDACE DAT //////////////////////
    //validace hry
    private boolean validateGame(Game g) {
        //zde si pouze oveřuji, aby něco bylo vyplněno v textu a v titlu
        if ((!g.getTitle().equals("")) && (!g.getText().equals(""))) {
            return true;
        } else {
            return false;
        }
    }

    //validace recenze
    private boolean validateReview(Review r) {
        //zde si pouze oveřuji, aby něco bylo vyplněno v textu a v titlu
        if ((!r.getDate().equals("")) && (r.getScore() > -1) && (r.getScore() < 101)) {
            return true;
        } else {
            return false;
        }
    }

    //validace položky
    private boolean validateItem(ItemForSale i) {
        //zde si pouze oveřuji, aby něco bylo vyplněno v textu a v titlu
        if ((!i.getNote().equals("")) && (i.getPrice() > 0)) {
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
            System.out.println("1");
        }

        //validace, aby všechna data byla vyplněna
        if ((u.getPassword().equals("")) || (u.getUsername().equals("")) || (u.getEmail().equals(""))) {
            warningMessageUser = "Některé povinné údaje je potřeba doplnit";
            check = false;
            System.out.println("2");
        }

        //validace hesla
        if ((u.getPassword().equals("12345678")) || (u.getPassword().equals("heslo"))) {
            warningMessageUser = "Špatně zabezpečené heslo";
            check = false;
            System.out.println("3");
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

    //validace uživatele - pouze jeho heslo při změnně
    private boolean validateOldUser(User u) {
        boolean check = true;

        //validace délky hesla
        if (u.getPassword().length() < 8) {
            warningMessageUser = "Heslo musí mít velikost alespoň 8 znaků";
            check = false;
            System.out.println("1");
        }

        //validace, aby všechna data byla vyplněna
        if ((u.getPassword().equals("")) || (u.getUsername().equals("")) || (u.getEmail().equals(""))) {
            warningMessageUser = "Některé povinné údaje je potřeba doplnit";
            check = false;
            System.out.println("2");
        }

        //validace hesla
        if ((u.getPassword().equals("12345678")) || (u.getPassword().equals("heslo"))) {
            warningMessageUser = "Špatně zabezpečené heslo";
            check = false;
            System.out.println("3");
        }

        if (check) {
            return true;
        } else {
            return false;
        }
    }
}