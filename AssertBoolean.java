public class AssertBoolean extends Assertion {
    private final Boolean b;

    public AssertBoolean(Boolean b) {
        this.b = b;
    }

    public AssertBoolean isNotNull() {
        if (b == null) {
            throw new AssertionError("Boolean is null");
        }
        return this;
    }

    public AssertBoolean isEqualTo(Boolean b) {
        if (!this.equals(b)) {
            throw new AssertionError("Boolean is not equal to " + b);
        }
        return this;
    }

    public AssertBoolean isTrue() {
        if (!b) {
            throw new AssertionError("Boolean is false");
        }
        return this;
    }

    public AssertBoolean isFalse() {
        if (b) {
            throw new AssertionError("Boolean is true");
        }
        return this;
    }
}
