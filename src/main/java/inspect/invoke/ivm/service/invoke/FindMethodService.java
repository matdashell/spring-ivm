package inspect.invoke.ivm.service.invoke;

import inspect.invoke.ivm.annotation.IvmMethod;
import inspect.invoke.ivm.dto.MethodData;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class FindMethodService {

    public MethodData inClassByName(Class<?> aClass, String methodName) {
        Method result = Arrays.stream(aClass.getMethods())
                .filter(method -> containsIvmMethodWithName(method, methodName))
                .collect(Collectors.toList())
                .get(0);

        return MethodData.builder()
                .name(result.getName())
                .method(result)
                .ivmMethod(result.getAnnotation(IvmMethod.class))
                .paramTypes(result.getParameterTypes())
                .build();
    }

    private boolean containsIvmMethodWithName(Method method, String methodName) {
        return Arrays.stream(method.getAnnotations())
                .anyMatch(annotation -> annotation.getClass().getName().equals(IvmMethod.class.getName())
                    && ((IvmMethod) annotation).name().equals(methodName));
    }
}
