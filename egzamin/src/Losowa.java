import java.util.Random;

public class Losowa extends Strategia {
    private static final Random random = new Random();

    private final double prWiny;
    private final double prUniewinnienia;

    public Losowa(double prWiny, double prUniewinnienia) throws Exception {
        if (prWiny > 1 || prUniewinnienia > 1 || prUniewinnienia + prWiny > 1 || prUniewinnienia < 0 || prWiny < 0) {
            throw new Exception("nieprawidłowe prawdopodobieństwa");
        }

        this.prWiny = prWiny;
        this.prUniewinnienia = prUniewinnienia;
    }

    @Override
    public Sąd.Werdykt werdykt(Sprawa sprawa) {
        double x = random.nextDouble();
        if (x < prWiny)
            return Sąd.Werdykt.WINA;
        if (x < prWiny + prUniewinnienia)
            return Sąd.Werdykt.UNIEWINNIENIE;
        return Sąd.Werdykt.UMORZENIE;
    }
}
