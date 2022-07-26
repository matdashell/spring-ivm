package inspect.invoke.ivm.service.invoke;

import inspect.invoke.ivm.dto.MethodData;
import inspect.invoke.ivm.interfaces.IvmObject;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static inspect.invoke.ivm.consts.Consts.DEFAULT_METHOD_NAME_IMPL;

@Service
public class FindParamsService {

    public List<Object> inMethod(MethodData methodData) {
        return Arrays.stream(methodData.getMethod().getParameterTypes())
                .map(aClass -> {
                    if (implementsIvmObject(aClass)) {
                        return getDefaultObjectImpl(aClass);
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private boolean implementsIvmObject(Class<?> aClass) {
        Class<?>[] interfaces = aClass.getInterfaces();
        return Arrays.asList(interfaces).contains(IvmObject.class);
    }

    private Object getDefaultObjectImpl(Class<?> aClass) {
        try {
            Object instance = aClass.getDeclaringClass().getDeclaredConstructor().newInstance();
            Method method = instance.getClass().getMethod(DEFAULT_METHOD_NAME_IMPL);
            return method.invoke(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
