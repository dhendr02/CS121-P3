public class AssertInteger {

    private final int value;

    public AssertInteger(int value) {
        this.value = value;
    }

    public AssertInteger isEqualTo(int i2) {
        if (value != i2) {
            throw new RuntimeException("Integer is not equal to " + i2);
        }
        return this;
    }

    public AssertInteger isLessThan(int i2) {
        if (value >= i2) {
            throw new RuntimeException("Integer is not less than " + i2);
        }
        return this;
    }

    public AssertInteger isGreaterThan(int i2) {
        if (value <= i2) {
            throw new RuntimeException("Integer is not greater than " + i2);
        }
        return this;
    }
}
