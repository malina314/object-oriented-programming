public class ŁącznaKwotaKar extends Strategia {
    @Override
    public Sąd.Werdykt werdykt(Sprawa sprawa) {
        if (sprawa.oskarżyciel().łącznaKwotaRządanychKarWSprawachUmorzonych() >
                sprawa.oskarżony().łącznaKwotaRządanychKarWSprawachUmorzonych())
            return Sąd.Werdykt.UNIEWINNIENIE;
        if (sprawa.oskarżyciel().łącznaKwotaRządanychKarWSprawachUmorzonych() <
                sprawa.oskarżony().łącznaKwotaRządanychKarWSprawachUmorzonych())
            return Sąd.Werdykt.WINA;
        return Sąd.Werdykt.UMORZENIE;
    }
}
