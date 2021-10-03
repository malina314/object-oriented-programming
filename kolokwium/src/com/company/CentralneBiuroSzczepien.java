package com.company;

import java.util.ArrayList;

public class CentralneBiuroSzczepien {
    private int ileZaszczepionych;
    private PunktSzczepien[] punktySzczepien;

    public CentralneBiuroSzczepien(PunktSzczepien[] punktySzczepien) {
        this.ileZaszczepionych = 0;
        this.punktySzczepien = punktySzczepien;
        Pacjent.inicjalizujBiuro(this);
        PunktSzczepien.inicjalizujBiuro(this);
    }

    public PunktSzczepien znajdzPunktSzczepien(int adres, Szczepionka szczepionka, int odleglosc) {
        ArrayList<PunktSzczepien> potencjalnePunkty = new ArrayList<PunktSzczepien>();

        // znajdowanie punktów pasujących pacjentowi
        for (PunktSzczepien p : punktySzczepien) {
            if (p.adres() % 10000 == adres % 10000          // sprawdzamy zgodnosc wojewodztw
                    && Math.abs(p.adres() - adres) < odleglosc
                    && p.maSzczepionke(szczepionka)) {
                potencjalnePunkty.add(p);
            }
        }

        ArrayList<PunktSzczepien> punktyZNajkrotszymCzasem = new ArrayList<PunktSzczepien>();
        int min = Integer.MAX_VALUE;

        // znajdowanie punktów w których można zaszczepić się najwcześniej
        for (PunktSzczepien p : potencjalnePunkty) {
            if (p.dzien() < min) {
                min = p.dzien();
                punktyZNajkrotszymCzasem.clear();
                punktyZNajkrotszymCzasem.add(p);
            } else if (p.dzien() == min) {
                punktyZNajkrotszymCzasem.add(p);
            }
        }

        PunktSzczepien najblizszy = null;
        min = Integer.MAX_VALUE;

        // znajdowanie najbliższego punktu
        for (PunktSzczepien p : punktyZNajkrotszymCzasem) {
            if (Math.abs(p.adres() - adres) < min) {
                min = Math.abs(p.adres() - adres);
                najblizszy = p;
            }
        }

        return najblizszy;
    }

    public void zwiekszLiczbeZaszczepionych() {
        ileZaszczepionych++;
    }
}
