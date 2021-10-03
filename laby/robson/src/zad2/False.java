package zad2;

public class False extends Wyrażenie {
    @Override
    public double wykonaj() {
        return 0;
    }

    @Override
    public String toJava() {
        return toJavaHelper("0");
    }
}
