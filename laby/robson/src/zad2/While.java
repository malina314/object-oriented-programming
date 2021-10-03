package zad2;

public class While extends Wyrażenie {
    protected Wyrażenie warunek;
    protected Wyrażenie blok;

    public While(Wyrażenie warunek, Wyrażenie blok) {
        this.warunek = warunek;
        this.blok = blok;
    }

    @Override
    public double wykonaj() {
        double wartosc = 0;
        while (warunek.wykonaj() != 0) {
            wartosc = blok.wykonaj();
        }
        return wartosc;
    }

    @Override
    public String toJava() {
        String indeks = Indeks.indeks();
        return toJavaHelper("{ double tmp_" + indeks + " = 0; while (" + warunek.toJava() +
                " != 0) { tmp_" + indeks + " = " + blok.toJava() + ";} return tmp_" + indeks + "; }");
    }
}
