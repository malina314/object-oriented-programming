package zad2;

public abstract class WyrażenieArytmetycznoLogincze extends Wyrażenie {
    protected Wyrażenie argument1;
    protected Wyrażenie argument2;

    public WyrażenieArytmetycznoLogincze(Wyrażenie argument1, Wyrażenie argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }
}
