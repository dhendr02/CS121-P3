public class AssertInteger extends Assertion {
    private final Integer i;

    public AssertInteger(Integer i) {
        this.i = i;
    }

    public AssertInteger isNotNull() {
        if (i == null) {
            throw new AssertionError("Integer is null");
        }
        return this;
    }

    public AssertInteger isEqualTo(Integer i2) {
        if (!i.equals(i2)) {
            throw new AssertionError("Integer is not equal to " + i2);
        }
        return this;
    }

    public AssertInteger isLessThan(Integer i2) {
        if (i >= i2) {
            throw new AssertionError("Integer is not less than " + i2);
        }
        return this;
    }

    public AssertInteger isGreaterThan(Integer i2) {
        if (i <= i2) {
            throw new AssertionError("Integer is not greater than " + i2);
        }
        return this;
    }
}
