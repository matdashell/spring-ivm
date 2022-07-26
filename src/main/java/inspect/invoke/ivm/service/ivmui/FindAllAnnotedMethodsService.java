package inspect.invoke.ivm.service.ivmui;

import inspect.invoke.ivm.annotation.IvmMethod;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindAllAnnotedMethodsService {

    public List<Method> get(Object object) {
        return Arrays.stream(object.getClass().getMethods())
                .filter(this::containsAnnotationIvmMethod)
                .collect(Collectors.toList());
    }

    private boolean containsAnnotationIvmMethod(Method method) {
        return Arrays.stream(method.getAnnotations())
                .anyMatch(annotation -> annotation.getClass().getName().equals(IvmMethod.class.getName()));
    }
}
