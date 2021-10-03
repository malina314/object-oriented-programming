package zad2;

public class Not extends WyrażenieArytmetycznoLogincze {
    public Not(Wyrażenie w1) {
        super(w1, null);
    }

    @Override
    public double wykonaj() {
        return argument1.wykonaj() == 0 ? 1 : 0;
    }

    @Override
    public String toJava() {
        String arg1 = argument1.toJava();
        return toJavaHelper(arg1 + " == 0 ? 1 : 0");
    }
}
