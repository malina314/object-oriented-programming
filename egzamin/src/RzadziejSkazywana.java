public class RzadziejSkazywana extends Strategia {
    @Override
    public Sąd.Werdykt werdykt(Sprawa sprawa) {
        if (sprawa.oskarżyciel().liczbaWyroków() > sprawa.oskarżony().liczbaWyroków())
            return Sąd.Werdykt.UNIEWINNIENIE;
        if (sprawa.oskarżyciel().liczbaWyroków() < sprawa.oskarżony().liczbaWyroków())
            return Sąd.Werdykt.WINA;
        return Sąd.Werdykt.UMORZENIE;
    }
}
