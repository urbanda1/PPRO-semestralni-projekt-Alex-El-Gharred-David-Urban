package mainProject.services;


public class MainMethods implements MainInterface {
}

//    private List<MainMethods> inzeraty = new ArrayList<MainMethods>();
//
//    @Override
//    public List<MainMethods> getInzeraty() {
//        return Collections.unmodifiableList(inzeraty);
//    }
//
//    @Override
//    public void pridej(MainMethods i) {
//        inzeraty.add(i);
//        int maxId = 0;
//        // vygenerovat nove id (bezne dela databaze)
//        for (MainMethods inz : inzeraty) {
//            if (inz.getId() > maxId) maxId = inz.getId();
//        }
//        i.setId(maxId + 1);
//        Collections.sort(inzeraty, new Comparator<MainMethods>() {
//
//            public int compare(MainMethods o1, MainMethods o2) {
//                return o1.getDatum().compareTo(o2.getDatum());
//            }
//
//        });
//    }
//
//    @Override
//    public void odstran(MainMethods i) {
//        inzeraty.remove(i);
//    }
//
//    @Override
//    public void odstran(int id) {
//        inzeraty.remove(getById(id));
//    }
//
//    @Override
//    public void uprav(MainMethods i) {
//        for (int j = 0; j < inzeraty.size(); j++) {
//            if (i.getId() == inzeraty.get(j).getId()) {
//                inzeraty.set(j, i);
//                return;
//            }
//        }
//    }
//
//    @Override
//    public MainMethods getById(int id) {
//        for (MainMethods i : inzeraty) {
//            if (i.getId() == id) {
//                return i;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public List<MainMethods> getInzeratyByKategorie(String kategorie) {
//        List<MainMethods> vysledky = new ArrayList<MainMethods>();
//        for (MainMethods i : inzeraty) {
//            if (kategorie.equals(i.getKategorie())) {
//                vysledky.add(i);
//            }
//        }
//        return vysledky;
//    }
