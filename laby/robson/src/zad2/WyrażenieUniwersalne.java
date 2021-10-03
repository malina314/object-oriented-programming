package zad2;

import java.util.List;

public class WyrażenieUniwersalne {
    protected double wartosc;
    protected String typ;
    protected String nazwa;
    protected WyrażenieUniwersalne argument1;
    protected WyrażenieUniwersalne argument2;
    protected WyrażenieUniwersalne warunek;
    protected WyrażenieUniwersalne blok_prawda;
    protected WyrażenieUniwersalne blok_falsz;
    protected WyrażenieUniwersalne blok;
    protected WyrażenieUniwersalne wartosc_przypisania;
    protected List<WyrażenieUniwersalne> instrukcje;
}
