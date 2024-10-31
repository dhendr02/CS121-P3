public class AssertString extends Assertion {
    private final String s;

    public AssertString(String s) {
        this.s = s;
    }

    public AssertString isNotNull() {
        if (s == null) {
            throw new AssertionError("String is null");
        }
        return this;
    }

    public AssertString isNull() {
        if (s != null) {
            throw new AssertionError("String is not null");
        }
        return this;
    }

    public AssertString isEqualTo(Object o) {
        if (!s.equals(o)) {
            throw new AssertionError("String is not equal to " + o);
        }
        return this;
    }

    public AssertString isNotEqualTo(Object o) {
        if (s.equals(o)) {
            throw new AssertionError("String is equal to " + o);
        }
        return this;
    }

    public AssertString startsWith(String s2) {
        if (!s.startsWith(s2)) {
            throw new AssertionError("String does not start with " + s2);
        }
        return this;
    }

    public AssertString isEmpty() {
        if (!s.isEmpty()) {
            throw new AssertionError("String is not empty");
        }
        return this;
    }

    public AssertString contains(String s2) {
        if (!s.contains(s2)) {
            throw new AssertionError("String does not contain " + s2);
        }
        return this;
    }


}
