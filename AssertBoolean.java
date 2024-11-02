public class AssertBoolean {

    private final boolean value;

    public AssertBoolean(boolean value) {
        this.value = value;
    }

    public AssertBoolean isEqualTo(boolean b2) {
        if (value != b2) {
            throw new RuntimeException("Boolean is not equal to " + b2);
        }
        return this;
    }

    public AssertBoolean isTrue() {
        if (!value) {
            throw new RuntimeException("Boolean is not true");
        }
        return this;
    }

    public AssertBoolean isFalse() {
        if (value) {
            throw new RuntimeException("Boolean is not false");
        }
        return this;
    }
}
