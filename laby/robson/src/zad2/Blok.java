package zad2;

import java.util.ArrayList;

public class Blok extends Wyrażenie {
    protected ArrayList<Wyrażenie> instrukcje;

    public Blok(ArrayList<Wyrażenie> instrukcje) {
        this.instrukcje = instrukcje;
    }

    @Override
    public double wykonaj() {
        double wartosc = 0;
        for (Wyrażenie w : instrukcje) {
            wartosc = w.wykonaj();
        }
        return wartosc;
    }

    @Override
    public String toJava() {
        StringBuilder instrukcjeJavy = new StringBuilder();
        if (instrukcje.size() > 0) {
            instrukcjeJavy.append("{");
            for (int i = 0; i < instrukcje.size() - 1; i++) {
                instrukcjeJavy.append(instrukcje.get(i).toJava()).append(";");
            }
            instrukcjeJavy.append("return ").append(instrukcje.get(instrukcje.size() - 1).toJava()).append(";}");
        } else {
            instrukcjeJavy.append("0");
        }
        return toJavaHelper(instrukcjeJavy.toString());
    }
}
