import java.util.function.DoubleSupplier;
import java.util.HashMap;
public class SkonwertowanyProgram5 {
  public static void main(String[] args) {
    HashMap<String, Double> zmienneGlobalne = new HashMap<>();    System.out.println(((DoubleSupplier)() ->{ double tmp_1 = 0; while (((DoubleSupplier)() ->(((DoubleSupplier)() ->{ if (zmienneGlobalne.containsKey("index")) { return zmienneGlobalne.get("index"); } else { zmienneGlobalne.put("index", 0.0); return 0; } }).getAsDouble() < ((DoubleSupplier)() ->{ if (zmienneGlobalne.containsKey("numer")) { return zmienneGlobalne.get("numer"); } else { zmienneGlobalne.put("numer", 0.0); return 0; } }).getAsDouble()) ? 1 : 0).getAsDouble() != 0) { tmp_1 = ((DoubleSupplier)() ->0).getAsDouble();} return tmp_1; }).getAsDouble());
  }
}