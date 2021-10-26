package mainProject.web;

import mainProject.model.Inzerat;
import mainProject.služby.PametoveUlozisteInzeratu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class InzeratController {
    private PametoveUlozisteInzeratu inzeraty = new PametoveUlozisteInzeratu();
    private String kategorie;

    @RequestMapping("/")
    public ModelAndView zobrazitMain() {
        if (kategorie == null) {
            kategorie = "";
        }

        ModelAndView model = new ModelAndView("main");

        List<Inzerat> vysledky;

        vysledky = inzeraty.getInzeratyByKategorie(kategorie);

        if ((kategorie.equals("Nákup")) || (kategorie.equals("Prodej")) || (kategorie.equals("Výměna"))) {
            model.addObject("inzeraty", vysledky);
        } else {
            model.addObject("inzeraty", inzeraty.getInzeraty());
        }
        return model;
    }

    @RequestMapping("/create-form")
    public ModelAndView zobrazitCreate() {
        Inzerat i = new Inzerat();
        ModelAndView model = new ModelAndView("create");
        model.addObject("inzerat", i);

        return model;
    }

    @RequestMapping("/edit-form")
    public ModelAndView zobrazitEdit(@RequestParam("id") int id) {
        ModelAndView model = new ModelAndView("edit");
        Inzerat inzeratEdit = inzeraty.getById(id);

        model.addObject("inzeratEdit", inzeratEdit);

        return model;
    }

    @PostMapping("/editInzerat")
    public String editInzerat(@ModelAttribute("inzeratEdit") Inzerat i) {

        inzeraty.getById(i.getId()).setText(i.getText());
        inzeraty.getById(i.getId()).setKategorie(i.getKategorie());
        inzeraty.getById(i.getId()).setCena(i.getCena());

        return "redirect:/";
    }

    @PostMapping("/saveInzerat")
    public String create(@ModelAttribute("inzerat") Inzerat i) {

        inzeraty.pridej(i);

        return "redirect:/";
    }

    @PostMapping("/filterInzeratyByKategorie")
    public String zobrazFiltr(@RequestParam("kategorie") String k) {
        kategorie = k;

        return "redirect:/";
    }

    @RequestMapping("/deleteInzerat")
    public String deleteInzerat(@RequestParam("heslo") String heslo) {

        for (Inzerat inzerat : inzeraty.getInzeraty()) {
            if (inzerat.getHesloProUpravu().equals(heslo)) {
                inzeraty.odstran(inzerat);
                return "redirect:/";
            }
        }
        return "redirect:/";
    }
}