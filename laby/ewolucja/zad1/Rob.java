package zad1;

import java.util.Random;

public class Rob {
    private enum Kierunek {GÓRA, PRAWO, DÓŁ, LEWO}

    private static final Kierunek[] kierunki = Kierunek.values();
    private static final Random random = new Random();
    private static Symulacja symulacja;

    private static class Współrzędne {
        private int x;
        private int y;

        private Współrzędne(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private Współrzędne copy() {
            return new Współrzędne(x, y);
        }

        private int x(int d) {
            return ((x + d) % symulacja.parametry().rozmiar_planszy_x() + symulacja.parametry().rozmiar_planszy_x()) %
                    symulacja.parametry().rozmiar_planszy_x();
        }

        private int y(int d) {
            return ((y + d) % symulacja.parametry().rozmiar_planszy_y() + symulacja.parametry().rozmiar_planszy_y()) %
                    symulacja.parametry().rozmiar_planszy_y();
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    private Współrzędne współrzędne;
    private Kierunek kierunek;
    private int wiek;
    private double energia;
    private final StringBuilder program;

    public Rob(double energia, String program) {
        assert(symulacja != null);

        this.energia = energia;
        this.program = new StringBuilder(program);
        wiek = 0;
        kierunek = kierunki[random.nextInt(kierunki.length)];
        współrzędne = new Współrzędne(random.nextInt(symulacja.parametry().rozmiar_planszy_x()),
            random.nextInt(symulacja.parametry().rozmiar_planszy_y()));
    }

    private Rob(Rob rodzic) {
        energia = rodzic.energia * symulacja.parametry().ułamek_energii_rodzica();
        wiek = 0;
        współrzędne = rodzic.współrzędne.copy();
        kierunek = rodzic.odwrotny_kierunek();
        program = new StringBuilder(rodzic.program);
        if (program.length() > 0 && random.nextDouble() < symulacja.parametry().pr_usunięcia_instr()) {
            program.deleteCharAt(program.length() - 1);
        }
        if (random.nextDouble() < symulacja.parametry().pr_dodania_instr()) {
            program.append(symulacja.parametry().losowa_instrukcja(random));
        }
        if (program.length() > 0 && random.nextDouble() < symulacja.parametry().pr_zmiany_instr()) {
            program.setCharAt(random.nextInt(program.length()), symulacja.parametry().losowa_instrukcja(random));
        }
    }

    public static void ustaw_symulację(Symulacja s) {
        symulacja = s;
    }

    public boolean żyje() {
        return energia >= 0;
    }

    public int długość_programu() {
        return program.length();
    }

    public double energia() {
        return energia;
    }

    public int wiek() {
        return wiek;
    }

    public Rob powiel() {
        if (energia >= symulacja.parametry().limit_powielania() &&
                random.nextDouble() < symulacja.parametry().pr_powielenia()) {
            Rob r = new Rob(this);
            energia -= energia * symulacja.parametry().ułamek_energii_rodzica();
            return r;
        }
        return null;
    }

    public void wykonaj_program() {
        for (int i = 0; i < program.length(); i++) {
            switch (program.charAt(i)) {
                case 'l':
                    obróć_w_lewo();
                    break;
                case 'r':
                    obróć_w_prawo();
                    break;
                case 'i':
                    idź();
                    break;
                case 'w':
                    wąchaj();
                    break;
                case 'j':
                    jedz();
                    break;
            }
            energia--;
            if (energia < 0) {
                return;
            }
        }
        energia -= symulacja.parametry().koszt_tury();
        wiek++;
    }

    private void idź() {
        switch (kierunek) {
            case GÓRA:
                współrzędne.y = współrzędne.y(-1);
                break;
            case DÓŁ:
                współrzędne.y = współrzędne.y(1);
                break;
            case PRAWO:
                współrzędne.x = współrzędne.x(1);
                break;
            case LEWO:
                współrzędne.x = współrzędne.x(-1);
                break;
        }
        energia += symulacja.zjedz(współrzędne.x, współrzędne.y);
    }

    private void wąchaj() {
        if (symulacja.jest_pożywnienie(współrzędne.x(-1), współrzędne.y)) {
            kierunek = Kierunek.LEWO;
        } else if (symulacja.jest_pożywnienie(współrzędne.x(1), współrzędne.y)) {
            kierunek = Kierunek.PRAWO;
        } else if (symulacja.jest_pożywnienie(współrzędne.x, współrzędne.y(-1))) {
            kierunek = Kierunek.GÓRA;
        } else if (symulacja.jest_pożywnienie(współrzędne.x, współrzędne.y(1))) {
            kierunek = Kierunek.DÓŁ;
        }
    }

    private void jedz() {
        // rob może zjeść po skosie, a nie ma kierunków po skosie, więc symulacja zakłada, że rob po zjedzeniu
        // ustawia się w dokładnie tym samym kierunku, w którym znajdował się przed wykonaiem tego ruchu
        // z tego wynika że funkcja jedz() nie modyfikuje kierunku

        pętla:
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (symulacja.jest_pożywnienie(współrzędne.x(j), współrzędne.y(i))) {
                        przemieść_się(współrzędne.x(j), współrzędne.y(i));
                        energia += symulacja.zjedz(współrzędne.x, współrzędne.y);
                        break pętla;
                    }
                }
            }
        }
    }

    private void przemieść_się(int x, int y) {
        współrzędne = new Współrzędne(x, y);
    }

    private void obróć_w_prawo() {
        kierunek = kierunki[(kierunek.ordinal() + 1) % kierunki.length];
    }

    private void obróć_w_lewo() {
        kierunek = kierunki[(kierunek.ordinal() - 1 + kierunki.length) % kierunki.length];
    }

    private Kierunek odwrotny_kierunek() {
        return kierunki[(kierunek.ordinal() + 2) % kierunki.length];
    }

    public int współrzędna_x() {
        return współrzędne.x;
    }

    public int współrzędna_y() {
        return współrzędne.y;
    }

    @Override
    public String toString() {
        return "Rob{" +
            "współrzędne: " + współrzędne +
            ", kierunek: " + kierunek +
            ", wiek: " + wiek +
            ", energia: " + symulacja.round(energia) +
            ", program: " + program +
            '}';
    }
}
