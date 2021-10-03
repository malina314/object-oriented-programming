package zad2;

public abstract class Wyrażenie implements WyrażenieI {
    protected String toJavaHelper(String funkcja) {
        return "((DoubleSupplier)() ->" + funkcja + ").getAsDouble()";
    }
}
