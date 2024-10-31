import java.util.*;
import java.lang.reflect.*;

public class Unit {
    public static Map<String, Throwable> testClass(String name) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        // Where the results are stored
        Map<String, Throwable> results = new HashMap<>();

        // Lists for the methods that fall into each category
        List<Method> beforeClassMethods = new ArrayList<>();
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterClassMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();

        // This is how we get the name of the class
        Class<?> clazz = Class.forName(name);

        Method[] methods = clazz.getDeclaredMethods();

        // Add the methods to the corresponding List based on the name
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeClass.class)) {
                beforeClassMethods.add(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(AfterClass.class)) {
                afterClassMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }

        // Alphabetize each list based on method names
        beforeClassMethods.sort(Comparator.comparing(Method::getName));
        beforeMethods.sort(Comparator.comparing(Method::getName));
        testMethods.sort(Comparator.comparing(Method::getName));
        afterClassMethods.sort(Comparator.comparing(Method::getName));
        afterMethods.sort(Comparator.comparing(Method::getName));


        Object instance = clazz.getDeclaredConstructor().newInstance();





        return results;

    }

    public static Map<String, Object[]> quickCheckClass(String name) {
	throw new UnsupportedOperationException();
    }
}