public class Assertion {
    /* You'll need to change the return type of the assertThat methods */
    public static AssertObject assertThat(Object o) {
        return new AssertObject(o);
    }
    public static AssertString assertThat(String s) {

        return new AssertString(s);
    }
    public static AssertBoolean assertThat(boolean b) {

        return new AssertBoolean(b);
    }
    public static AssertInteger assertThat(int i) {

        return new AssertInteger(i);
    }


}