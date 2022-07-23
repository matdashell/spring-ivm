package inspect.invoke.ivm.factory;

import lombok.Data;

@Data
public class InvokeArg {
    private String classLocation;
    private String arg;

    public Class<?> getClassLocation() {
        try {
            return Class.forName(classLocation);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
