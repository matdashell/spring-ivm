package inspect.invoke.ivm.dto;

import java.util.List;

public class Param {

    private boolean alone;
    private Object object;
    private List<Object> objects;

    public void add(Object o) {
        alone = true;
        object = o;
    }

    public void add(List<Object> o) {
        alone = false;
        objects = o;
    }

    public boolean isAlone() {
        return alone;
    }

    public Object getObject() {
        return object;
    }

    public List<Object> getObjects() {
        return objects;
    }
}
