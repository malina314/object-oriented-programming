package com.company;

public class Pacjent {
    private int adres;
    private int zasieg;
    private int dataSzczepienia;
    private Szczepionka szczepionkaJakaChce;
    private PunktSzczepien punktSzczepien;
    private static CentralneBiuroSzczepien biuro;

    public Pacjent(int adres, int zasieg, Szczepionka szczepionka) {
        this.adres = adres;
        this.zasieg = zasieg;
        this.szczepionkaJakaChce = szczepionka;
        this.punktSzczepien = null;
        this.dataSzczepienia = -1;
    }

    public void znajdzPunktSzczepien() {
        punktSzczepien = biuro.znajdzPunktSzczepien(adres, szczepionkaJakaChce, zasieg);
    }

    public void zarejestrujSie() {
        dataSzczepienia = punktSzczepien.zarejestrujPacjenta();
    }

    public void zaszczepSie() {
        punktSzczepien.zaszczepPacjenta();
    }

    public static void inicjalizujBiuro(CentralneBiuroSzczepien b) {
        biuro = b;
    }
}
