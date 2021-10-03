package zad1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Symulacja {
    public static class Parametry {
        private final int ile_tur;
        private final int pocz_ile_robów;
        private final int co_ile_wypisz;
        private final int ile_rośnie_jedzenie;
        private final int rozmiar_planszy_x;
        private final int rozmiar_planszy_y;

        private final double pocz_energia;
        private final double koszt_tury;
        private final double ile_daje_jedzenie;
        private final double limit_powielania;
        private final double ułamek_energii_rodzica;
        private final double pr_powielenia;
        private final double pr_usunięcia_instr;
        private final double pr_dodania_instr;
        private final double pr_zmiany_instr;

        private final String spis_instr;
        private final String pocz_progr;

        // w każdym polu trzymany jest numer tury w której pożywienie będzie dostępne
        // pola bez pożywienia mają wartość Integer.MAX_VALUE
        // lewy górny róg ma współrzędne (0,0)
        private final int[][] pożywienie;

        public int rozmiar_planszy_x() {
            return rozmiar_planszy_x;
        }

        public int rozmiar_planszy_y() {
            return rozmiar_planszy_y;
        }

        public double koszt_tury() {
            return koszt_tury;
        }

        public double limit_powielania() {
            return limit_powielania;
        }

        public double ułamek_energii_rodzica() {
            return ułamek_energii_rodzica;
        }

        public double pr_powielenia() {
            return pr_powielenia;
        }

        public double pr_usunięcia_instr() {
            return pr_usunięcia_instr;
        }

        public double pr_dodania_instr() {
            return pr_dodania_instr;
        }

        public double pr_zmiany_instr() {
            return pr_zmiany_instr;
        }

        public Parametry(int ile_tur, int pocz_ile_robów, int ile_rośnie_jedzenie, int co_ile_wypisz, int rozmiar_planszy_x,
                  int rozmiar_planszy_y, double pocz_energia, double koszt_tury, double ile_daje_jedzenie,
                  double limit_powielania, double ułamek_energii_rodzica, double pr_powielenia,
                  double pr_usunięcia_instr, double pr_dodania_instr, double pr_zmiany_instr, String spis_instr,
                  String pocz_progr, int[][] pożywienie) {
            this.ile_tur = ile_tur;
            this.pocz_ile_robów = pocz_ile_robów;
            this.ile_rośnie_jedzenie = ile_rośnie_jedzenie;
            this.co_ile_wypisz = co_ile_wypisz;
            this.rozmiar_planszy_x = rozmiar_planszy_x;
            this.rozmiar_planszy_y = rozmiar_planszy_y;

            this.pocz_energia = pocz_energia;
            this.koszt_tury = koszt_tury;
            this.ile_daje_jedzenie = ile_daje_jedzenie;
            this.limit_powielania = limit_powielania;
            this.ułamek_energii_rodzica = ułamek_energii_rodzica;
            this.pr_powielenia = pr_powielenia;
            this.pr_usunięcia_instr = pr_usunięcia_instr;
            this.pr_dodania_instr = pr_dodania_instr;
            this.pr_zmiany_instr = pr_zmiany_instr;

            this.spis_instr = spis_instr;
            this.pocz_progr = pocz_progr;
            this.pożywienie = pożywienie;
        }

        public char losowa_instrukcja(Random random) {
            return spis_instr.charAt(random.nextInt(spis_instr.length()));
        }
    }

    private final Parametry parametry;
    private ArrayList<Rob> roby;
    private final ArrayList<Rob> nowe_roby;
    private int nr_tury;
    private int zmarli;

    public Symulacja(String plansza_ścieżka, String parametry_ścieżka) {
        parametry = Wczytywanie.wczytaj(plansza_ścieżka, parametry_ścieżka);

        Rob.ustaw_symulację(this);

        roby = new ArrayList<>();
        for (int i = 0; i < parametry.pocz_ile_robów; i++) {
            roby.add(new Rob(parametry.pocz_energia, parametry.pocz_progr));
        }

        nowe_roby = new ArrayList<>();

        nr_tury = 0;
        zmarli = 0;
    }

    private int licz_pola_z_żywnością() {
        int ile = 0;
        for (int i = 0; i < parametry.rozmiar_planszy_y; i++) {
            for (int j = 0; j < parametry.rozmiar_planszy_x; j++) {
                if (parametry.pożywienie[i][j] <= nr_tury) {
                    ile++;
                }
            }
        }
        return ile;
    }

    public Parametry parametry() {
        return parametry;
    }

    public boolean jest_pożywnienie(int x, int y) {
        return parametry.pożywienie[y][x] <= nr_tury;
    }

    public double zjedz(int x, int y) {
        if (jest_pożywnienie(x, y)) {
            parametry.pożywienie[y][x] = nr_tury + parametry.ile_rośnie_jedzenie;
            return parametry.ile_daje_jedzenie;
        } else {
            return 0;
        }
    }

    private void symuluj() {
        for (int i = 1; i <= parametry.ile_tur; i++) {
            wykonaj_turę();
            zaktualizuj_roby();
            wypisz_krótkie_info();
            if (i % parametry.co_ile_wypisz == 0) {
                wypisz_stan_symulacji();
            }
        }
    }

    private void zaktualizuj_roby() {
        ArrayList<Rob> bufor = new ArrayList<>();
        for (Rob r : roby) {
            if (r.żyje()) {
                bufor.add(r);
            } else {
                zmarli++;
            }
        }
        bufor.addAll(nowe_roby);
        nowe_roby.clear();
        roby.clear();
        roby = bufor;
    }

    private void wypisz_krótkie_info() {
        //tura: 245, rob: 120, żywn: 340, prog: 3/4.56/78, energ: 1/4.34/26, wiek: 1/12.46/78
        System.out.print("tura: " + nr_tury + ", ");
        System.out.print("rob: " + roby.size() + ", ");
        System.out.print("żywn: " + licz_pola_z_żywnością() + ", ");
        String[] statystyki = statystyki();
        System.out.print("prog: " + statystyki[0] + ", ");
        System.out.print("energ: " + statystyki[1] + ", ");
        System.out.print("wiek: " + statystyki[2]);
        System.out.println();
    }

    private String[] statystyki() {
        if (roby.size() == 0) {
            return new String[]{"-/-/-", "-/-/-", "-/-/-"};
        }

        String[] statystyki = new String[3];
        int[] długości = new int[roby.size()];
        double[] energie = new double[roby.size()];
        int[] wieki = new int[roby.size()];

        for (int i = 0; i < roby.size(); i++) {
            długości[i] = roby.get(i).długość_programu();
            energie[i] = roby.get(i).energia();
            wieki[i] = roby.get(i).wiek();
        }

        statystyki[0] = Arrays.stream(długości).min().getAsInt() + "/" +
            round(Arrays.stream(długości).average().getAsDouble()) + "/" +
            Arrays.stream(długości).max().getAsInt();

        statystyki[1] = round(Arrays.stream(energie).min().getAsDouble()) + "/" +
                round(Arrays.stream(energie).average().getAsDouble()) + "/" +
                        round(Arrays.stream(energie).max().getAsDouble());

        statystyki[2] = Arrays.stream(wieki).min().getAsInt() + "/" +
                round(Arrays.stream(wieki).average().getAsDouble()) + "/" +
                Arrays.stream(wieki).max().getAsInt();

        return statystyki;
    }

    public double round(double x) {
        return Math.round(100 * x) / 100.;
    }

    private void wypisz_stan_symulacji() {
        System.out.println("========================= STAN SYMULACJI =========================");
        System.out.println("tura: " + nr_tury);

        System.out.println("### PLANSZA ###");
        System.out.println("pól z dostępną żywnością: " + licz_pola_z_żywnością());
        System.out.println("układ dostępnej żywności:");
        wypisz_układ_żywności();
        System.out.println("układ robów:");
        wypisz_układ_robów();

        System.out.println("### ROBY ###");
        System.out.println("w sumie: " + (roby.size() + zmarli));
        System.out.println("żyje: " + roby.size());
        System.out.println("zmarło: " + zmarli);
        System.out.println("informacje szczegółowe:");
        for (Rob r : roby) {
            System.out.println(r.toString());
        }
        System.out.println("==================================================================");
    }

    private void wypisz_układ_robów() {
        int[][] ile_robów = new int[parametry.rozmiar_planszy_y][parametry.rozmiar_planszy_x];
        for (Rob r : roby) {
            ile_robów[r.współrzędna_y()][r.współrzędna_x()]++;
        }
        int[] najdłuższa_liczba_w_kolumine = new int[parametry().rozmiar_planszy_x];
        for (int i = 0; i < parametry().rozmiar_planszy_x; i++) {
            najdłuższa_liczba_w_kolumine[i] = 1;
            for (int j = 0; j < parametry.rozmiar_planszy_y; j++) {
                najdłuższa_liczba_w_kolumine[i] = Math.max(najdłuższa_liczba_w_kolumine[i], (int)Math.log10(ile_robów[j][i]) + 1);
            }
        }
        for (int i = 0; i < parametry.rozmiar_planszy_y; i++) {
            for (int j = 0; j < parametry.rozmiar_planszy_x; j++) {
                String s = Integer.toString(ile_robów[i][j]);
                wypisz_znaki(' ', najdłuższa_liczba_w_kolumine[j] - s.length());
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    private void wypisz_układ_żywności() {
        for (int i = 0; i < parametry().rozmiar_planszy_y; i++) {
            for (int j = 0; j < parametry.rozmiar_planszy_x; j++) {
                System.out.print(jest_pożywnienie(j, i) ? "x" : "·");
            }
            System.out.println();
        }
    }

    private void wypisz_znaki(char c, int ile) {
        for (int i = 0; i < ile; i++) {
            System.out.print(c);
        }
    }

    private void wykonaj_turę() {
        nr_tury++;
        for (Rob r : roby) {
            r.wykonaj_program();
            Rob nowy = r.powiel();
            if (nowy != null) {
                nowe_roby.add(nowy);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new Error("niewłaściwa liczba argumentów funkcji main");
        }
        Symulacja symulacja = new Symulacja(args[0], args[1]);
        symulacja.wypisz_stan_symulacji();
        symulacja.symuluj();
        symulacja.wypisz_stan_symulacji();
    }
}
