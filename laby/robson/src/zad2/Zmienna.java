package zad2;

public class Zmienna extends Wyrażenie {
    private final String nazwa;
    private final ZmienneGlobalne zmienneGlobalne;

    public Zmienna(String nazwa, ZmienneGlobalne zmienneGlobalne) {
        this.nazwa = nazwa;
        this.zmienneGlobalne = zmienneGlobalne;
    }

    @Override
    public double wykonaj() {
        return zmienneGlobalne.wartosc(nazwa);
    }

    @Override
    public String toJava() {
        return toJavaHelper("{ if (zmienneGlobalne.containsKey(\"" + nazwa + "\")) { return zmienneGlobalne.get(\"" +
                        nazwa + "\"); } else { zmienneGlobalne.put(\"" + nazwa + "\", 0.0); return 0; } }");
    }
}
