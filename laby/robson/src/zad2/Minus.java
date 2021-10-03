package zad2;

public class Minus extends WyrażenieArytmetycznoLogincze {
    public Minus(Wyrażenie w1, Wyrażenie w2) {
        super(w1, w2);
    }

    @Override
    public double wykonaj() {
        return argument1.wykonaj() - argument2.wykonaj();
    }

    @Override
    public String toJava() {
        String arg1 = argument1.toJava();
        String arg2 = argument2.toJava();
        return toJavaHelper(arg1 + " - " + arg2);
    }
}
