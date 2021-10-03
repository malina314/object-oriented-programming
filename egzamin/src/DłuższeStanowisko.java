public class DłuższeStanowisko extends Strategia {
    @Override
    public Sąd.Werdykt werdykt(Sprawa sprawa) {
        if (sprawa.stanowsikoOskarżyciela().length() > sprawa.stanowsikoOskarżonego().length())
            return Sąd.Werdykt.WINA;
        if (sprawa.stanowsikoOskarżyciela().length() < sprawa.stanowsikoOskarżonego().length())
            return Sąd.Werdykt.UNIEWINNIENIE;
        return Sąd.Werdykt.UMORZENIE;
    }
}
