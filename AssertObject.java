public class AssertObject extends Assertion {
    private final Object obj;

    public AssertObject(Object obj) {
        this.obj = obj;
    }

    public AssertObject isNotNull() {
        if (obj == null) {
            throw new AssertionError("Object is null");
        }
        return this;
    }

    public AssertObject isNull() {
        if (obj != null) {
            throw new AssertionError("Object is null");
        }
        return this;
    }

    public AssertObject isEqualTo(AssertObject o2) {
        if (!this.equals(o2)) {
            throw new AssertionError("Object is not equal to " + o2);
        }
        return this;
    }

    public AssertObject isNotEqualTo(AssertObject o2) {
        if (this.equals(o2)) {
            throw new AssertionError("Object is equal to " + o2);
        }
        return this;
    }

    public AssertObject isInstanceOf(Class<?> c) {
        if (!c.isInstance(obj)) {
            throw new AssertionError("Object is not instance of " + c);
        }
        return this;
    }
}
