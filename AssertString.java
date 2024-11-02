public class AssertString extends AssertObject {

    private final String str;

    public AssertString(String str) {
        super(str);
        this.str = str;
    }

    public AssertString isNotNull() {
        super.isNotNull();
        return this;
    }

    public AssertString isNull() {
        super.isNull();
        return this;
    }

    public AssertString isEqualTo(Object o) {
        super.isEqualTo(o);
        return this;
    }

    public AssertString isNotEqualTo(Object o) {
        super.isNotEqualTo(o);
        return this;
    }

    public AssertString startsWith(String s2) {
        if (!str.startsWith(s2)) {
            throw new RuntimeException("String does not start with " + s2);
        }
        return this;
    }

    public AssertString isEmpty() {
        if (!str.isEmpty()) {
            throw new RuntimeException("String is not empty");
        }
        return this;
    }

    public AssertString contains(String s2) {
        if (!str.contains(s2)) {
            throw new RuntimeException("String does not contain " + s2);
        }
        return this;
    }
}
