public class ExampleTests {
    @BeforeClass public static void setupClass() {
        System.out.println("Setting up class resources");
    }

    @Before public void setup() {
        System.out.println("Setting up before each test");
    }

    @Test public void test1() {
        System.out.println("Running test1");
    }
    @Test public void test2() {
        System.out.println("Running test2");
    }

    @After public void tearDown() {
        System.out.println("Tearing down after each test");
    }

    @AfterClass public static void tearDownClass() {
        System.out.println("Tearing down class resources");
    }
}
