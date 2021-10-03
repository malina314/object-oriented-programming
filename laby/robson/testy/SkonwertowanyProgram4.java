import java.util.function.DoubleSupplier;
import java.util.HashMap;
public class SkonwertowanyProgram4 {
  public static void main(String[] args) {
    HashMap<String, Double> zmienneGlobalne = new HashMap<>();    System.out.println(((DoubleSupplier)() ->{ if (zmienneGlobalne.containsKey("jakaśZmienna")) { return zmienneGlobalne.get("jakaśZmienna"); } else { zmienneGlobalne.put("jakaśZmienna", 0.0); return 0; } }).getAsDouble());
  }
}