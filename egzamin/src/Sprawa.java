public class Sprawa {
    private final Mieszkaniec oskarżyciel;
    private final Mieszkaniec oskarżony;
    private final int żądanaKara;
    private String stanowsikoOskarżyciela;
    private String stanowsikoOskarżonego;

    public Sprawa(Mieszkaniec oskarżyciel, Mieszkaniec oskarżony, int żądanaKara) {
        this.oskarżyciel = oskarżyciel;
        this.oskarżony = oskarżony;
        this.żądanaKara = żądanaKara;
    }

    public void dodajStanowiskoOskarżyciela(String s) {
        stanowsikoOskarżyciela = s;
    }

    public void dodajStanowiskoOskarżonego(String s) {
        stanowsikoOskarżonego = s;
    }

    public Mieszkaniec oskarżyciel() {
        return oskarżyciel;
    }

    public Mieszkaniec oskarżony() {
        return oskarżony;
    }

    // na wypadek gdyby pojawili się sędziowie stosujący strategię uzależnioną od żądanej kary
    public int żądanaKara() {
        return żądanaKara;
    }

    public String stanowsikoOskarżyciela() {
        return stanowsikoOskarżyciela;
    }

    public String stanowsikoOskarżonego() {
        return stanowsikoOskarżonego;
    }
}
