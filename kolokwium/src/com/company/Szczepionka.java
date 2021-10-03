package com.company;

import java.util.Objects;

public class Szczepionka {
    private String nazwa;
    private String nazwaProducenta;
    private int dawka;

    public Szczepionka(String nazwa, String nazwaProducenta, int dawka) {
        this.nazwa = nazwa;
        this.nazwaProducenta = nazwaProducenta;
        this.dawka = dawka;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Szczepionka that = (Szczepionka) o;
        return dawka == that.dawka && Objects.equals(nazwa, that.nazwa) && Objects.equals(nazwaProducenta, that.nazwaProducenta);
    }
}
