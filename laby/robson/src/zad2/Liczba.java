package zad2;

public class Liczba extends Wyrażenie {
    private final double wartosc;

    public Liczba(double wartosc) {
        this.wartosc = wartosc;
    }

    public double wykonaj() {
        return wartosc;
    }

    @Override
    public String toJava() {
        return toJavaHelper(Double.toString(wartosc));
    }
}
