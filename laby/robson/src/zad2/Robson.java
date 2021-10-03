package zad2;

import com.squareup.moshi.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Robson {
    private String json;
    private Wyrażenie program;
    private final ZmienneGlobalne zmienneGlobalne;

    public void fromJSON(String filename) throws NieprawidlowyProgram {
        StringBuilder input = new StringBuilder();

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                input.append(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Nieprawidłowa ścieżka do pliku.");
            System.exit(1);
        }

        json = input.toString();

        String jsonDoKonwersji = json.replaceAll("\"wartosc\"\\s*:\\s*\\{",  "\"wartosc_przypisania\":\\{");
        WyrażenieUniwersalne wyrażenie = null;
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<WyrażenieUniwersalne> jsonAdapter = moshi.adapter(WyrażenieUniwersalne.class);

        try {
            wyrażenie = jsonAdapter.fromJson(jsonDoKonwersji);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd konwersji z formatu JSON w bibliotece Moshi.");
            System.exit(1);
        }

        program = konwertujWyrażenie(wyrażenie);

        System.out.println("Wczytywanie programu zakończone pomyślnie.");
    }

    public void toJSON(String filename) {
        try {
            File plik = new File(filename);
            plik.createNewFile();
        } catch (IOException e) {
            System.err.println("Błąd podczas tworzenia pliku.");
            System.exit(1);
        }

        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(json);
            myWriter.close();
            System.out.println("Pomyślnie zapisano JSONa do pliku " + filename);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku.");
            System.exit(1);
        }
    }

    public void toJava(String filename) {
        try {
            File plik = new File(filename);
            plik.createNewFile();
        } catch (IOException e) {
            System.err.println("Błąd podczas tworzenia pliku.");
            System.exit(1);
        }

        String className = "";
        Pattern regex = Pattern.compile("(?<=/|^)[\\p{L}_$][\\p{L}\\d_$]*(?=\\.java$)");
        Matcher m = regex.matcher(filename);

        if (m.find()) {
            className = m.group(0);
        } else {
            System.err.println("Niepoprawna nazwa pliku. Nazwa pliku musi być poprawną nazwą klasy w Javie i posiadać rozszerzenie \".java\".");
            System.exit(1);
        }

        Indeks.wyczyść();
        StringBuilder kodJavy = new StringBuilder();

        kodJavy.append("import java.util.function.DoubleSupplier;\n");
        kodJavy.append("import java.util.HashMap;\n");
        kodJavy.append("public class ").append(className).append(" {\n");
        kodJavy.append("  public static void main(String[] args) {\n");
        kodJavy.append("    HashMap<String, Double> zmienneGlobalne = new HashMap<>();");
        kodJavy.append("    System.out.println(").append(program != null ? program.toJava() : "0").append(");\n");
        kodJavy.append("  }\n}");

        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(kodJavy.toString());
            myWriter.close();
            System.out.println("Pomyślnie zapisano program w Javie do pliku " + filename);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku.");
            System.exit(1);
        }
    }

    public double wykonaj() throws BladWykonania {
        if (program != null) {
            try {
                zmienneGlobalne.wyczyść();
                return program.wykonaj();
            } catch (ArithmeticException e) {
                throw new BladWykonania();
            }
        }
        return 0;
    }

    private Wyrażenie konwertujWyrażenie(WyrażenieUniwersalne w) {
        if (w == null || w.typ == null) {
            return null;
        }
        switch (w.typ) {
            case "Plus":
                return new Plus(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "Minus":
                return new Minus(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "Razy":
                return new Razy(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "Dzielenie":
                return new Dzielenie(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "And":
                return new And(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "Or":
                return new Or(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "Not":
                return new Not(konwertujWyrażenie(w.argument1));
            case "<":
                return new Mniejsze(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "<=":
                return new MniejszeRówne(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case ">":
                return new Większe(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case ">=":
                return new WiększeRówne(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "==":
                return new Równe(konwertujWyrażenie(w.argument1), konwertujWyrażenie(w.argument2));
            case "Blok":
                ArrayList<Wyrażenie> instrukcje = new ArrayList<>();
                for (WyrażenieUniwersalne i : w.instrukcje) {
                    instrukcje.add(konwertujWyrażenie(i));
                }
                return new Blok(instrukcje);
            case "If":
                return new If(
                        konwertujWyrażenie(w.warunek),
                        konwertujWyrażenie(w.blok_prawda),
                        konwertujWyrażenie(w.blok_falsz)
                    );
            case "While":
                return new While(konwertujWyrażenie(w.warunek), konwertujWyrażenie(w.blok));
            case "Liczba":
                return new Liczba(w.wartosc);
            case "True":
                return new True();
            case "False":
                return new False();
            case "Przypisanie":
                return new Przypisanie(w.nazwa, konwertujWyrażenie(w.wartosc_przypisania), zmienneGlobalne);
            case "Zmienna":
                return new Zmienna(w.nazwa, zmienneGlobalne);
            default:
                return null;
        }
    }

    public Robson() {
        zmienneGlobalne = new ZmienneGlobalne();
        json = "{}";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Robson r = new Robson();

        System.out.println("Witej w interpreterze języka ROBSON!");
        System.out.println("Podaj ścieżkę do pliku z kodem ROBSON w formacie JSON.");

        // wczytywanie
        String filename = scanner.next();
        try {
            r.fromJSON(filename);
        } catch (NieprawidlowyProgram nieprawidlowyProgram) {
            System.err.println("Nieprawidłowy program.");
            System.exit(1);
        }

        System.out.println("Wybierz polecenie podając jego numer.");
        System.out.println("1) wykonaj program");
        System.out.println("2) zapisz program do pliku w formacie JSON");
        System.out.println("3) konwertuj program na język Java i zapisz do pliku");

        int nr;
        try {
            nr = scanner.nextInt();
        } catch (InputMismatchException e) {
            nr = -1;
        }

        switch (nr) {
            case 1:
                try {
                    System.out.println(r.wykonaj());
                } catch (BladWykonania bladWykonania) {
                    System.err.println("Błąd wykonania programu.");
                    System.exit(1);
                }
                break;
            case 2:
                System.out.println("Podaj ścieżkę do pliku, w którym ma zostać zapisany program.");
                filename = scanner.next();
                r.toJSON(filename);
                break;
            case 3:
                System.out.println("Podaj ścieżkę do pliku, w którym ma zostać zapisany program.");
                filename = scanner.next();
                r.toJava(filename);
                break;
            default:
                System.out.println("Nieprawidłowy numer polecenia.");
        }
        scanner.close();
    }

    public static class NieprawidlowyProgram extends Exception {
    }

    public static class BladWykonania extends Exception {
    }
}
