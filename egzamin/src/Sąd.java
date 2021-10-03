import java.util.ArrayList;

public class Sąd {
    public enum Werdykt {
        UMORZENIE, WINA, UNIEWINNIENIE
    }

    private ArrayList<Sędzia> sędziowie;
    private Sąd sądNadrzędny;
    private boolean jestLiściem;
    private int indeksOstatnioOrzekającegoSędziego;
    private int sądyPodrzędne;

    public Sąd(ArrayList<Sędzia> sędziowie, Sąd sądNadrzędny) {
        this.sędziowie = sędziowie;
        this.sądNadrzędny = sądNadrzędny;
        jestLiściem = true;
        indeksOstatnioOrzekającegoSędziego = -1;
    }

    // zakładam że Minister Sprawiedliwości Bajtocji pilnuje, aby sądy tworzyły strukturę drzewa i nie stworzy cylku
    // lub innych niezgodnych z Prawem Bajtocji struktur
    public void zmieńSądNadrzędny(Sąd nowySądNadrzędny) {
        if (sądNadrzędny != null) {
            sądNadrzędny.sądyPodrzędne--;
            if (sądNadrzędny.sądyPodrzędne == 0) {
                sądNadrzędny.jestLiściem = true;
            }
        }
        nowySądNadrzędny.jestLiściem = false;
        nowySądNadrzędny.sądyPodrzędne++;
        sądNadrzędny = nowySądNadrzędny;
    }

    public boolean możnaSięOdwołać() {
        return sądNadrzędny != null;
    }

    public boolean jestLiściem() {
        return jestLiściem;
    }

    public Werdykt rozpatrzSprawę(Sprawa sprawa) {
        if (sprawa.oskarżyciel().maImmunitet() || sprawa.oskarżony().maImmunitet()) {
            return null;
        }

        sprawa.dodajStanowiskoOskarżyciela(sprawa.oskarżyciel().złóżPisemneStanowisko());
        sprawa.dodajStanowiskoOskarżonego(sprawa.oskarżony().złóżPisemneStanowisko());

        return wybierzSędziego().wydajWerdykt(sprawa);
    }

    private Sędzia wybierzSędziego() {
        indeksOstatnioOrzekającegoSędziego++;
        if (indeksOstatnioOrzekającegoSędziego == sędziowie.size()) {
            indeksOstatnioOrzekającegoSędziego = 0;
        }
        return sędziowie.get(indeksOstatnioOrzekającegoSędziego);
    }

    // system sądowniczy Bajtocji jest przyjazny mieszkańcom dlatego mieszkaniec chcąc się odwołać nie musi iść do
    // kolejnego sądu tylko składa odwołanie w tym samym sądzie a ten sąd przekazuje sprawę do wyższej instancji
    public Werdykt złóżOdwołanie(Sprawa sprawa) {
        return sądNadrzędny.rozpatrzSprawę(sprawa);
    }
}
