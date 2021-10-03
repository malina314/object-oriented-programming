package zad2;

public class Indeks {
    private static int indeks;

    public static String indeks() {
        indeks++;
        return Integer.toString(indeks);
    }

    public static void wyczyść() {
        indeks = 0;
    }
}
