import java.lang.reflect.*;
public class RunTests {
  public static void main(String[] args) throws Exception {
    Class<?> type = Class.forName("com.example.tiptime.TipCalculatorTest");
    Object instance = type.getConstructor().newInstance();
    int passed = 0;
    for (Method method : type.getDeclaredMethods()) {
      if (method.isAnnotationPresent(org.junit.Test.class)) {
        try { method.invoke(instance); System.out.println("PASS " + method.getName()); passed++; }
        catch (InvocationTargetException failure) { throw new AssertionError(method.getName(), failure.getCause()); }
      }
    }
    System.out.println("TOTAL: " + passed + " tests passed");
    if (passed != 13) throw new AssertionError("Expected 13 tests");
  }
}
