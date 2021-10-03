package zad2;

import java.util.HashMap;

public class ZmienneGlobalne {
    HashMap<String, Double> zmienneGlobalne;

    public ZmienneGlobalne() {
        zmienneGlobalne = new HashMap<>();
    }

    public void przypisz(String zmienna, double wartosc) {
        zmienneGlobalne.put(zmienna, wartosc);
    }

    public double wartosc(String zmienna) {
        if (zmienneGlobalne.containsKey(zmienna)) {
            return zmienneGlobalne.get(zmienna);
        } else {
            zmienneGlobalne.put(zmienna, 0.0);
            return 0;
        }
    }

    public void wyczyść() {
        zmienneGlobalne.clear();
    }
}
