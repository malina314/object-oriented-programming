package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wczytywanie {
    private static class Błąd_parametru extends Exception {
        public Błąd_parametru(String s) {
            super(s);
        }
    }

    static Symulacja.Parametry wczytaj(String plansza_ścieżka, String parametry_ścieżka) {
        int ile_tur = -1;
        int pocz_ile_robów = -1;
        int ile_rośnie_jedzenie = -1;
        int co_ile_wypisz = -1;
        int rozmiar_planszy_x = -1;
        int rozmiar_planszy_y = -1;

        double pocz_energia = -1;
        double koszt_tury = -1;
        double ile_daje_jedzenie = -1;
        double limit_powielania = -1;
        double ułamek_energii_rodzica = -1;
        double pr_powielenia = -1;
        double pr_usunięcia_instr = -1;
        double pr_dodania_instr = -1;
        double pr_zmiany_instr = -1;

        String spis_instr = null;
        String pocz_progr = null;
        int[][] pożywienie = null;

        ArrayList<String> plansza = new ArrayList<>();

        // plansza
        try {
            File plik_plansza = new File(plansza_ścieżka);
            Scanner scanner = new Scanner(plik_plansza);
            while (scanner.hasNextLine()) {
                String linia = scanner.nextLine();
                if (rozmiar_planszy_x == -1) {
                    rozmiar_planszy_x = linia.length();
                } else if (rozmiar_planszy_x != linia.length()) {
                    System.out.println("nieprawidłowa plansza");
                    scanner.close();
                    System.exit(1);
                }
                plansza.add(linia);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Błąd otwarcia pliku " + plansza_ścieżka);
            System.exit(1);
        }

        rozmiar_planszy_y = plansza.size();

        if (rozmiar_planszy_x == 0 || rozmiar_planszy_y == 0) {
            System.out.println("nieprawidłowa plansza");
            System.exit(1);
        }

        pożywienie = new int[rozmiar_planszy_y][rozmiar_planszy_x];

        for (int i = 0; i < rozmiar_planszy_y; i++) {
            for (int j = 0; j < rozmiar_planszy_x; j++) {
                if (plansza.get(i).charAt(j) == 'x') {
                    pożywienie[i][j] = 0;
                } else if (plansza.get(i).charAt(j) == ' ') {
                    pożywienie[i][j] = Integer.MAX_VALUE;
                } else {
                    System.out.println("nieprawidłowa plansza");
                    System.exit(1);
                }
            }
        }

        // parametry
        try {
            File plik_parametry = new File(parametry_ścieżka);
            Scanner scanner = new Scanner(plik_parametry);
            while (scanner.hasNextLine()) {
                String[] linia = scanner.nextLine().split(" ");
                if (linia.length != 2) {
                    throw new Błąd_parametru("niepoprawna linia z parametrem");
                }

                // początkowy program może pojawić się na wejściu przed wczytaniem spisu instrukcji
                // dlatego sprawdzenie następuje później
                switch (linia[0]) {
                    case "ile_tur":
                        if (ile_tur != -1) {
                            throw new Błąd_parametru("powtórzenie parametru ile_tur");
                        }
                        ile_tur = Integer.parseInt(linia[1]);
                        if (ile_tur < 0) {
                            throw new Błąd_parametru("liczba tur musi być nieujemna");
                        }
                        break;
                    case "pocz_ile_robów":
                        if (pocz_ile_robów != -1) {
                            throw new Błąd_parametru("powtórzenie parametru pocz_ile_robów");
                        }
                        pocz_ile_robów = Integer.parseInt(linia[1]);
                        if (pocz_ile_robów < 0) {
                            throw new Błąd_parametru("liczba robów musi być nieujemna");
                        }
                        break;
                    case "ile_rośnie_jedzenie":
                        if (ile_rośnie_jedzenie != -1) {
                            throw new Błąd_parametru("powtórzenie parametru ile_rośnie_jedzenie");
                        }
                        ile_rośnie_jedzenie = Integer.parseInt(linia[1]);
                        if (ile_rośnie_jedzenie < 0) {
                            throw new Błąd_parametru("czas wzrostu jedzenia musi być nieujemny");
                        }
                        break;
                    case "co_ile_wypisz":
                        if (co_ile_wypisz != -1) {
                            throw new Błąd_parametru("powtórzenie parametru co_ile_wypisz");
                        }
                        co_ile_wypisz = Integer.parseInt(linia[1]);
                        if (co_ile_wypisz <= 0) {
                            throw new Błąd_parametru("wartość parametru co_ile_wypisz musi być dodatnia");
                        }
                        break;
                    case "pocz_energia":
                        if (pocz_energia != -1) {
                            throw new Błąd_parametru("powtórzenie parametru pocz_energia");
                        }
                        pocz_energia = Double.parseDouble(linia[1]);
                        if (pocz_energia < 0) {
                            throw new Błąd_parametru("początkowa energia musi być nieujemna");
                        }
                        break;
                    case "koszt_tury":
                        if (koszt_tury != -1) {
                            throw new Błąd_parametru("powtórzenie parametru koszt_tury");
                        }
                        koszt_tury = Double.parseDouble(linia[1]);
                        if (koszt_tury < 0) {
                            throw new Błąd_parametru("koszt tury musi być nieujemny");
                        }
                        break;
                    case "ile_daje_jedzenie":
                        if (ile_daje_jedzenie != -1) {
                            throw new Błąd_parametru("powtórzenie parametru ile_daje_jedzenie");
                        }
                        ile_daje_jedzenie = Double.parseDouble(linia[1]);
                        if (ile_daje_jedzenie < 0) {
                            throw new Błąd_parametru("wartość parametru ile_daje_jedzenie musi być nieujemna");
                        }
                        break;
                    case "limit_powielania":
                        if (limit_powielania != -1) {
                            throw new Błąd_parametru("powtórzenie parametru limit_powielania");
                        }
                        limit_powielania = Double.parseDouble(linia[1]);
                        if (limit_powielania < 0) {
                            throw new Błąd_parametru("limit powielania musi być nieujemny");
                        }
                        break;
                    case "ułamek_energii_rodzica":
                        if (ułamek_energii_rodzica != -1) {
                            throw new Błąd_parametru("powtórzenie parametru ułamek_energii_rodzica");
                        }
                        ułamek_energii_rodzica = Double.parseDouble(linia[1]);
                        if (ułamek_energii_rodzica < 0 || ułamek_energii_rodzica > 1) {
                            throw new Błąd_parametru("ułamek energii rodzica musi być z przedziału [0,1]");
                        }
                        break;
                    case "pr_powielenia":
                        if (pr_powielenia != -1) {
                            throw new Błąd_parametru("powtórzenie parametru pr_powielenia");
                        }
                        pr_powielenia = Double.parseDouble(linia[1]);
                        if (pr_powielenia < 0 || pr_powielenia > 1) {
                            throw new Błąd_parametru("prawdopodobieństwo powielenia musi być z przedziału [0,1]");
                        }
                        break;
                    case "pr_usunięcia_instr":
                        if (pr_usunięcia_instr != -1) {
                            throw new Błąd_parametru("powtórzenie parametru pr_usunięcia_instr");
                        }
                        pr_usunięcia_instr = Double.parseDouble(linia[1]);
                        if (pr_usunięcia_instr < 0 || pr_usunięcia_instr > 1) {
                            throw new Błąd_parametru("prawdopodobieństwo usunięcia instrukcji musi być z przedziału [0,1]");
                        }
                        break;
                    case "pr_dodania_instr":
                        if (pr_dodania_instr != -1) {
                            throw new Błąd_parametru("powtórzenie parametru pr_dodania_instr");
                        }
                        pr_dodania_instr = Double.parseDouble(linia[1]);
                        if (pr_dodania_instr < 0 || pr_dodania_instr > 1) {
                            throw new Błąd_parametru("prawdopodobieństwo dodania instrukcji musi być z przedziału [0,1]");
                        }
                        break;
                    case "pr_zmiany_instr":
                        if (pr_zmiany_instr != -1) {
                            throw new Błąd_parametru("powtórzenie parametru pr_zmiany_instr");
                        }
                        pr_zmiany_instr = Double.parseDouble(linia[1]);
                        if (pr_zmiany_instr < 0 || pr_zmiany_instr > 1) {
                            throw new Błąd_parametru("prawdopodobieństwo zmiany instrukcji musi być z przedziału [0,1]");
                        }
                        break;
                    case "spis_instr":
                        if (spis_instr != null) {
                            throw new Błąd_parametru("powtórzenie parametru spis_instr");
                        }
                        spis_instr = linia[1];
                        if (!sprawdź_spis_instr(spis_instr)) {
                            throw new Błąd_parametru("spis instrukcji zawiera niedozwolone instrukcje");
                        }
                        break;
                    case "pocz_progr":
                        if (pocz_progr != null) {
                            throw new Błąd_parametru("powtórzenie parametru pocz_progr");
                        }
                        pocz_progr = linia[1];
                        break;
                    default:
                        throw new Błąd_parametru("błędna nazwa parametru");
                }
            }
            scanner.close();
        } catch (FileNotFoundException w) {
            System.out.println("Błąd otwarcia pliku " + parametry_ścieżka);
            System.exit(1);
        } catch (NumberFormatException w) {
            System.err.println("niepoprawna wartość parametru");
            System.exit(1);
        } catch (Błąd_parametru w) {
            System.err.println(w.getMessage());
            System.exit(1);
        }

        if (ile_tur == -1 || pocz_ile_robów == -1 || co_ile_wypisz == -1 || pocz_energia == -1 || koszt_tury == -1 ||
                ile_daje_jedzenie == -1 || ile_rośnie_jedzenie == -1 || limit_powielania == -1 ||
                ułamek_energii_rodzica == -1 || pr_powielenia == -1 || pr_usunięcia_instr == -1 ||
                pr_dodania_instr == -1 || pr_zmiany_instr == -1 || spis_instr == null || pocz_progr == null) {
            throw new Error("nie wszystkie parametry zostały zainicjalizowane");
        }

        if (!sprawdź_program(pocz_progr, spis_instr)) {
            throw new Error("początkowy program zawiera niedozwolone instrukcje");
        }

        return new Symulacja.Parametry(ile_tur, pocz_ile_robów, ile_rośnie_jedzenie, co_ile_wypisz, rozmiar_planszy_x,
                rozmiar_planszy_y, pocz_energia, koszt_tury, ile_daje_jedzenie, limit_powielania, ułamek_energii_rodzica,
                pr_powielenia, pr_usunięcia_instr, pr_dodania_instr, pr_zmiany_instr, spis_instr, pocz_progr, pożywienie);
    }

    // zwraca true jeżeli spis jest poprawny
    private static boolean sprawdź_spis_instr(String spis) {
        String dozwolone_instrukcje = "lpiwj";
        for (int i = 0; i < spis.length(); i++) {
            if (dozwolone_instrukcje.indexOf(spis.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    // zwraca true jeżeli program jest poprawny
    private static boolean sprawdź_program(String program, String spis_instrukcji) {
        for (int i = 0; i < program.length(); i++) {
            if (spis_instrukcji.indexOf(program.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }
}