import java.util.Random;

public class Mieszkaniec {
    private String imię;
    private boolean immunitet;
    private int stanKonta;
    private int liczbaWyroków;
    private int łącznaKwotaRządanychKarWSprawachUmorzonych;

    public Mieszkaniec(String imię) {
        this.imię = imię;
    }

    public Mieszkaniec(String imię, boolean immunitet) {
        this(imię);
        this.immunitet = immunitet;
    }

    public boolean maImmunitet() {
        return immunitet;
    }

    // w Bajtocji można nadać mieszkańcowi immunitet np. z racji pełnionego stanowiska innego niż sędzia lub za zasługi
    // dla kraju. Immunitet jest nadawany dożywotnio i jest nieodwołalny.
    public void nadajImmunitet() {
        immunitet = true;
    }

    public int liczbaWyroków() {
        return liczbaWyroków;
    }

    public int łącznaKwotaRządanychKarWSprawachUmorzonych() {
        return łącznaKwotaRządanychKarWSprawachUmorzonych;
    }

    public void inicjujSpór(Sąd sąd, Mieszkaniec oskarżony, int żądanaKara) throws Exception {
        if (!sąd.jestLiściem()) {
            throw new Exception("Spór można zainicjować tylko w sądzie najniższej instancji.");
        }

        Sprawa sprawa = new Sprawa(this, oskarżony, żądanaKara);

        Sąd.Werdykt werdykt = sąd.rozpatrzSprawę(sprawa);

        if (werdykt == null) {
            return;
        }

        while ((werdykt == Sąd.Werdykt.UNIEWINNIENIE || werdykt == Sąd.Werdykt.UMORZENIE) && sąd.możnaSięOdwołać()) {
            werdykt = sąd.złóżOdwołanie(sprawa);
        }

        switch (werdykt) {
            case WINA:
                oskarżony.zapłaćKarę(żądanaKara);
                break;
            case UNIEWINNIENIE:
                zapłaćKarę(żądanaKara);
            case UMORZENIE:
                łącznaKwotaRządanychKarWSprawachUmorzonych += żądanaKara;
        }
    }

    public String złóżPisemneStanowisko() {
        return imię + stanKonta * new Random().nextInt();
    }

    private void zapłaćKarę(int kara) {
        stanKonta -= kara;
        liczbaWyroków++;
    }
}
