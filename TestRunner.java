import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class TestRunner {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        try {
            Map<String, Throwable> results = Unit.testClass("ExampleTests");

            for (Map.Entry<String, Throwable> entry : results.entrySet()) {
                String testName = entry.getKey();
                Throwable error = entry.getValue();
                if (error == null) {
                    System.out.println(testName + " passed");
                } else {
                    System.out.println(testName + " failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
