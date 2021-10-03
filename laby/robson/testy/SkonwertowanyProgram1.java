import java.util.function.DoubleSupplier;
import java.util.HashMap;
public class SkonwertowanyProgram1 {
  public static void main(String[] args) {
    HashMap<String, Double> zmienneGlobalne = new HashMap<>();    System.out.println(((DoubleSupplier)() ->{return ((DoubleSupplier)() ->((DoubleSupplier)() ->7.0).getAsDouble() + ((DoubleSupplier)() ->8.0).getAsDouble()).getAsDouble();}).getAsDouble());
  }
}