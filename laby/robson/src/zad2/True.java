package zad2;

public class True extends Wyrażenie {
    @Override
    public double wykonaj() {
        return 1;
    }

    @Override
    public String toJava() {
        return toJavaHelper("1");
    }
}
