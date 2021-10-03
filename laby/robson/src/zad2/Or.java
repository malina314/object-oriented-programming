package zad2;

public class Or extends WyrażenieArytmetycznoLogincze {
    public Or(Wyrażenie w1, Wyrażenie w2) {
        super(w1, w2);
    }

    @Override
    public double wykonaj() {
        return (argument1.wykonaj() != 0 || argument2.wykonaj() != 0) ? 1 : 0;
    }

    @Override
    public String toJava() {
        String arg1 = argument1.toJava();
        String arg2 = argument2.toJava();
        return toJavaHelper("(" + arg1 + " != 0 || " + arg2 + " != 0) ? 1 : 0");
    }
}
