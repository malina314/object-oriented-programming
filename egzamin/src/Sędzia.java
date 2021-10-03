public class Sędzia extends Mieszkaniec {
    private final Strategia strategia;

    public Sędzia(String imię, Strategia strategia) {
        super(imię, true);
        this.strategia = strategia;
    }

    public Sąd.Werdykt wydajWerdykt(Sprawa sprawa) {
        return strategia.werdykt(sprawa);
    }
}
