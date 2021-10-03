package com.company;

public class PunktSzczepien {
    private int adres;
    private int przepustowosc;
    private int dzien;
    private int ileTegoDnia;
    private Szczepionka[] szczepionki;
    private static CentralneBiuroSzczepien biuro;

    public PunktSzczepien(int adres, int przepustowosc, Szczepionka[] szczepionki) {
        this.adres = adres;
        this.przepustowosc = przepustowosc;
        this.dzien = 1;
        this.ileTegoDnia = 0;
        this.szczepionki = szczepionki;
    }

    public int adres() {
        return adres;
    }

    public int dzien() {
        return dzien;
    }

    public boolean maSzczepionke(Szczepionka x) {
        for (Szczepionka s : szczepionki) {
            if (s.equals(x)) {
                return true;
            }
        }
        return false;
    }

    public int zarejestrujPacjenta() {
        ileTegoDnia++;
        if (ileTegoDnia == przepustowosc) {
            dzien++;
            ileTegoDnia = 0;
            return dzien - 1; // takie rozwiązanie ułatwia zwracanie wolnego dnia
        }
        return dzien;
    }

    public void zaszczepPacjenta() {
        biuro.zwiekszLiczbeZaszczepionych();
    }

    public static void inicjalizujBiuro(CentralneBiuroSzczepien b) {
        biuro = b;
    }
}
