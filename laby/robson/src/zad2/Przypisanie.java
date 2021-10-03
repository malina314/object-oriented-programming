package zad2;

public class Przypisanie extends Wyrażenie {
    private final String nazwa;
    private final Wyrażenie wartosc;
    private final ZmienneGlobalne zmienneGlobalne;

    public Przypisanie(String nazwa, Wyrażenie wartosc, ZmienneGlobalne zmienneGlobalne) {
        this.nazwa = nazwa;
        this.wartosc = wartosc;
        this.zmienneGlobalne = zmienneGlobalne;
    }

    @Override
    public double wykonaj() {
        double w = wartosc.wykonaj();
        zmienneGlobalne.przypisz(nazwa, w);
        return w;
    }

    @Override
    public String toJava() {
        String indeks = Indeks.indeks();
        return toJavaHelper("{ double tmp_" + indeks + " = " + wartosc.toJava() + "; zmienneGlobalne.put(\"" +
                nazwa + "\", tmp_" + indeks + "); return tmp_" + indeks + "; }");
    }
}
