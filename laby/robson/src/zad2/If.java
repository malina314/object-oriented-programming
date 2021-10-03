package zad2;

public class If extends Wyrażenie {
    protected Wyrażenie warunek;
    protected Wyrażenie blok_prawda;
    protected Wyrażenie blok_fałsz;

    public If(Wyrażenie warunek, Wyrażenie blok_prawda, Wyrażenie blok_fałsz) {
        this.warunek = warunek;
        this.blok_prawda = blok_prawda;
        this.blok_fałsz = blok_fałsz;
    }

    @Override
    public double wykonaj() {
        if (warunek.wykonaj() != 0) {
            return  blok_prawda.wykonaj();
        } else if (blok_fałsz != null) {
            return blok_fałsz.wykonaj();
        }
        return 0;
    }

    @Override
    public String toJava() {
        StringBuilder kodJavy = new StringBuilder();
        kodJavy.append("{ if (").append(warunek.toJava()).append(" != 0) {");
        kodJavy.append("return ").append(blok_prawda.toJava()).append(";} else {return ");
        if (blok_fałsz != null) {
            kodJavy.append(blok_fałsz.toJava());
        } else {
            kodJavy.append("0");
        }
        kodJavy.append(";} }");
        return toJavaHelper(kodJavy.toString());
    }
}
