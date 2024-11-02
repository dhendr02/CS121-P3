public class AssertObject {

    private final Object obj;

    public AssertObject(Object obj) {
        this.obj = obj;
    }

    public AssertObject isNotNull() {
        if (obj == null) {
            throw new RuntimeException("Object is null");
        }
        return this;
    }

    public AssertObject isNull() {
        if (obj != null) {
            throw new RuntimeException("Object is not null");
        }
        return this;
    }

    public AssertObject isEqualTo(Object o2) {
        if (!obj.equals(o2)) {
            throw new RuntimeException("Object is not equal to " + o2);
        }
        return this;
    }

    public AssertObject isNotEqualTo(Object o2) {
        if (obj.equals(o2)) {
            throw new RuntimeException("Object is equal to " + o2);
        }
        return this;
    }

    public AssertObject isInstanceOf(Class<?> c) {
        if (!c.isInstance(obj)) {
            throw new RuntimeException("Object is not instance of " + c);
        }
        return this;
    }
}
