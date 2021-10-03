package zad2;

import static org.junit.Assert.*;
import org.junit.Test;

public class RobsonTest {
    @Test
    public void test_fromJSON() throws Robson.NieprawidlowyProgram {
        Robson r = new Robson();

        r.fromJSON("testy/kod1.json");
        r.fromJSON("testy/kod2.json");
        r.fromJSON("testy/kod3.json");
        r.fromJSON("testy/kod4.json");
        r.fromJSON("testy/kod5.json");
    }

    @Test
    public void test_toJSON() throws Robson.NieprawidlowyProgram {
        Robson r = new Robson();

        // test wypisania pustego programu
        r.toJSON("testy/test_toJSON_pusty.json");

        r.fromJSON("testy/kod1.json");
        r.toJSON("testy/test_toJSON_kod1.json");

        r.fromJSON("testy/kod2.json");
        r.toJSON("testy/test_toJSON_kod2.json");

        r.fromJSON("testy/kod3.json");
        r.toJSON("testy/test_toJSON_kod3.json");

        r.fromJSON("testy/kod4.json");
        r.toJSON("testy/test_toJSON_kod4.json");

        r.fromJSON("testy/kod5.json");
        r.toJSON("testy/test_toJSON_kod5.json");
    }

    @Test
    public void test_wykonaj() throws Robson.BladWykonania, Robson.NieprawidlowyProgram {
        Robson r = new Robson();
        double delta = Double.MIN_VALUE;

        // test wykonania pustego programu
        assertEquals(0, r.wykonaj(), delta);

        r.fromJSON("testy/kod1.json");
        assertEquals(15, r.wykonaj(), delta);

        r.fromJSON("testy/kod2.json");
        assertEquals(55, r.wykonaj(), delta);

        // szybkie potęgowanie aczkolwiek trochę naokoło ze względu na brak modulo i dzielenia całkowitego
        // więc w praktyce wyjdzie wolniej niż normalne
        r.fromJSON("testy/kod3.json");
        assertEquals(Math.pow(69, 42), r.wykonaj(), Math.pow(69, 42) / 100);

        r.fromJSON("testy/kod4.json");
        assertEquals(0, r.wykonaj(), delta);

        r.fromJSON("testy/kod5.json");
        assertEquals(0, r.wykonaj(), delta);
    }

    @Test
    public void test_toJava() throws Robson.NieprawidlowyProgram {
        Robson r = new Robson();

        r.toJava("testy/SkonwertowanyProgram0.java");

        r.fromJSON("testy/kod1.json");
        r.toJava("testy/SkonwertowanyProgram1.java");

        r.fromJSON("testy/kod2.json");
        r.toJava("testy/SkonwertowanyProgram2.java");

        r.fromJSON("testy/kod3.json");
        r.toJava("testy/SkonwertowanyProgram3.java");

        r.fromJSON("testy/kod4.json");
        r.toJava("testy/SkonwertowanyProgram4.java");

        r.fromJSON("testy/kod5.json");
        r.toJava("testy/SkonwertowanyProgram5.java");
    }
}