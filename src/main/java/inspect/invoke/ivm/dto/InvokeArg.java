package inspect.invoke.ivm.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvokeArg {
    private String classLocation;
    private Object arg;

    public Class<?> getGenericObjectClass() {
        try {
            if (((List<?>) arg).size() > 1) {
                return List.class;
            }
        } catch (ClassCastException e) {
            try {
                return Class.forName(classLocation);
            } catch (ClassNotFoundException ee) {
                throw new RuntimeException(ee);
            }
        }
        throw new RuntimeException();
    }

    public Class<?> getAbsoluteClass() {
        try {
            return Class.forName(classLocation);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
