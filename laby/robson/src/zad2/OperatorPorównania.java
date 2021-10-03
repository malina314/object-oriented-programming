package zad2;

public abstract class OperatorPorównania extends Wyrażenie {
    protected Wyrażenie argument1;
    protected Wyrażenie argument2;

    public OperatorPorównania(Wyrażenie argument1, Wyrażenie argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }
}
