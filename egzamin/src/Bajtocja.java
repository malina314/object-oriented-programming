import java.util.ArrayList;

public class Bajtocja {
    private ArrayList<Sąd> sądy;
    private ArrayList<Mieszkaniec> mieszkańcy;

    public Bajtocja(ArrayList<Sąd> sądy, ArrayList<Mieszkaniec> mieszkańcy) {
        this.sądy = sądy;
        this.mieszkańcy = mieszkańcy;
    }

    // zakładam, że Minister Sprawiedliwości Bajtocji pilnuje, aby sądy tworzyły strukturę drzewa i nie doda
    // więcej niż jednego sądu bez sądu nadrzędnego
    // w razie gdyby się pomylił może użyć metody zmieńSądNadrzędny
    public void dodajNowySąd(Sąd sądNadrzędny, ArrayList<Sędzia> sędziowie) {
        Sąd nowySąd = new Sąd(sędziowie, sądNadrzędny);
        sądy.add(nowySąd);
    }
}
