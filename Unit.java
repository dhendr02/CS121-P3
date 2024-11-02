import java.lang.annotation.Annotation;
import java.util.*;
import java.lang.reflect.*;

public class Unit {

    public static Map<String, Throwable> testClass(String name) throws Exception {
        // Store the results
        Map<String, Throwable> results = new HashMap<>();

        // Lists to store the methods to be run
        List<Method> beforeClassMethods = new ArrayList<>();
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> afterClassMethods = new ArrayList<>();

        // Get the class and its methods
        Class<?> clazz = Class.forName(name);
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            int annotations = 0;
            if (method.isAnnotationPresent(BeforeClass.class)) {
                if (!Modifier.isStatic(method.getModifiers())) throw new RuntimeException("@BeforeClass must be static");
                beforeClassMethods.add(method);
                annotations++;
            }
            if (method.isAnnotationPresent(Before.class)) {
                if (!Modifier.isPublic(method.getModifiers()) || Modifier.isStatic(method.getModifiers())) throw new RuntimeException("@Before must be public instance");
                beforeMethods.add(method);
                annotations++;
            }
            if (method.isAnnotationPresent(Test.class)) {
                if (!Modifier.isPublic(method.getModifiers()) || Modifier.isStatic(method.getModifiers())) throw new RuntimeException("@Test must be public instance");
                testMethods.add(method);
                annotations++;
            }
            if (method.isAnnotationPresent(After.class)) {
                if (!Modifier.isPublic(method.getModifiers()) || Modifier.isStatic(method.getModifiers())) throw new RuntimeException("@After must be public instance");
                afterMethods.add(method);
                annotations++;
            }
            if (method.isAnnotationPresent(AfterClass.class)) {
                if (!Modifier.isStatic(method.getModifiers())) throw new RuntimeException("@AfterClass must be static");
                afterClassMethods.add(method);
                annotations++;
            }
            if (annotations > 1) throw new RuntimeException("Only one annotation allowed per method");
        }

        // Sort all methods
        beforeClassMethods.sort(Comparator.comparing(Method::getName));
        beforeMethods.sort(Comparator.comparing(Method::getName));
        testMethods.sort(Comparator.comparing(Method::getName));
        afterMethods.sort(Comparator.comparing(Method::getName));
        afterClassMethods.sort(Comparator.comparing(Method::getName));

        // Run @BeforeClass methods
        for (Method method : beforeClassMethods) method.invoke(null);

        // Create instance and run tests
        Object instance = clazz.getDeclaredConstructor().newInstance();
        for (Method testMethod : testMethods) {
            for (Method before : beforeMethods) before.invoke(instance);

            try {
                testMethod.setAccessible(true);
                testMethod.invoke(instance);
                results.put(testMethod.getName(), null);
            } catch (InvocationTargetException e) {
                results.put(testMethod.getName(), e.getTargetException());
            } catch (Exception e) {
                results.put(testMethod.getName(), e);
            }

            for (Method after : afterMethods) after.invoke(instance);
        }

        // Run @AfterClass methods
        for (Method method : afterClassMethods) method.invoke(null);

        return results;
    }

    public static Map<String, Object[]> quickCheckClass(String name) throws Exception {
        Map<String, Object[]> results = new HashMap<>();
        Class<?> clazz = Class.forName(name);
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> propertyMethods = new ArrayList<>();

        // Filter for methods annotated with @Property and sort them alphabetically
        for (Method method : methods) {
            if (method.isAnnotationPresent(Property.class)) {
                propertyMethods.add(method);
            }
        }
        propertyMethods.sort(Comparator.comparing(Method::getName));

        Object instance = clazz.getDeclaredConstructor().newInstance(); // Create instance once

        for (Method method : propertyMethods) {
            List<List<Object>> argumentSets = new ArrayList<>();
            Parameter[] parameters = method.getParameters();

            for (Parameter parameter : parameters) {
                List<Object> generatedValues = new ArrayList<>();
                Annotation[] annotations = parameter.getAnnotations();

                if (annotations.length != 1) {
                    throw new RuntimeException("Each parameter must have exactly one annotation specifying its range or type.");
                }

                Annotation annotation = annotations[0];
                if (annotation instanceof IntRange) {
                    IntRange intRange = (IntRange) annotation;
                    for (int i = intRange.min(); i <= intRange.max(); i++) {
                        generatedValues.add(i);
                    }
                } else if (annotation instanceof StringSet) {
                    StringSet stringSet = (StringSet) annotation;
                    generatedValues.addAll(Arrays.asList(stringSet.strings()));
                } else if (annotation instanceof ListLength) {
                    ListLength listLength = (ListLength) annotation;
                    AnnotatedType listElementType = ((AnnotatedParameterizedType) parameter.getAnnotatedType()).getAnnotatedActualTypeArguments()[0];
                    IntRange intRange = listElementType.getAnnotation(IntRange.class);

                    if (intRange == null) {
                        throw new RuntimeException("List parameter with @ListLength must also have @IntRange on its element type.");
                    }

                    for (int len = listLength.min(); len <= listLength.max(); len++) {
                        generatedValues.addAll(generateAllIntegerLists(len, intRange));
                    }
                } else if (annotation instanceof ForAll) {
                    ForAll forAll = (ForAll) annotation;
                    String generatorMethodName = forAll.name();
                    int times = forAll.times();
                    Method generatorMethod = clazz.getDeclaredMethod(generatorMethodName);
                    for (int i = 0; i < times; i++) {
                        generatedValues.add(generatorMethod.invoke(instance));
                    }
                } else {
                    throw new RuntimeException("Unsupported annotation type on parameter.");
                }

                argumentSets.add(generatedValues);
            }

            List<Object[]> argumentCombinations = generateCombinations(argumentSets);
            argumentCombinations = argumentCombinations.subList(0, Math.min(argumentCombinations.size(), 100));
            boolean passedAll = true;

            for (Object[] args : argumentCombinations) {
                try {
                    boolean result = (Boolean) method.invoke(instance, args);
                    if (!result) {
                        results.put(method.getName(), args);
                        passedAll = false;
                        break;
                    }
                } catch (Throwable t) {
                    results.put(method.getName(), args);
                    passedAll = false;
                    break;
                }
            }

            if (passedAll) {
                results.put(method.getName(), null);
            }
        }

        return results;
    }

    private static List<Object[]> generateCombinations(List<List<Object>> argumentSets) {
        List<Object[]> combinations = new ArrayList<>();
        combineArguments(argumentSets, combinations, new Object[argumentSets.size()], 0);
        return combinations;
    }

    private static void combineArguments(List<List<Object>> argumentSets, List<Object[]> combinations, Object[] currentCombination, int depth) {
        if (depth == argumentSets.size()) {
            combinations.add(currentCombination.clone());
            return;
        }
        for (Object value : argumentSets.get(depth)) {
            currentCombination[depth] = value;
            combineArguments(argumentSets, combinations, currentCombination, depth + 1);
        }
    }

    private static List<List<Integer>> generateAllIntegerLists(int length, IntRange range) {
        List<List<Integer>> allLists = new ArrayList<>();
        int min = range.min();
        int max = range.max();
        generateListsRecursive(new ArrayList<>(), length, min, max, allLists);
        return allLists;
    }

    private static void generateListsRecursive(List<Integer> currentList, int length, int min, int max, List<List<Integer>> allLists) {
        if (currentList.size() == length) {
            allLists.add(new ArrayList<>(currentList));
            return;
        }
        for (int i = min; i <= max; i++) {
            currentList.add(i);
            generateListsRecursive(currentList, length, min, max, allLists);
            currentList.remove(currentList.size() - 1);
        }
    }
}
