package inspect.invoke.ivm.dto;

import inspect.invoke.ivm.annotation.IvmMethod;
import lombok.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class MethodData {

    private String name;
    private Method method;
    private Class<?>[] paramTypes;
    private IvmMethod ivmMethod;

    public Object invoke(Object object, Object[] params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
