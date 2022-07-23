package inspect.invoke.ivm.dto;

import lombok.Data;

@Data
public class InvokeArg {
    private String classLocation;
    private Object arg;

    public Class<?> getClassLocation() {
        try {
            return Class.forName(classLocation);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
