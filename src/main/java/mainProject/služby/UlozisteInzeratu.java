package mainProject.služby;

import mainProject.model.Inzerat;

import java.util.List;


public interface UlozisteInzeratu {
    public List<Inzerat> getInzeraty();

    public void pridej(Inzerat i);

    public void odstran(Inzerat i);

    public void odstran(int id);

    public void uprav(Inzerat i);

    public Inzerat getById(int id);

    public List<Inzerat> getInzeratyByKategorie(String kategorie);
}
