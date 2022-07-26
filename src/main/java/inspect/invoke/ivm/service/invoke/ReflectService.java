package inspect.invoke.ivm.service.invoke;

import inspect.invoke.ivm.dto.MethodData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReflectService {

    private final FindClassObjectService findClassObjectService;
    private final InitBeansService initBeansService;
    private final FindMethodService fIndMethodService;
    private final MapperService mapperService;

    public Object reflect(List<Object> json, String className, String methodName) {
        Object classObject = findClassObjectService.byClassName(className);
        Class<?> aClass = classObject.getClass();
        initBeansService.init(classObject, aClass);
        MethodData method = fIndMethodService.inClassByName(aClass, methodName);
        List<Object> params = mapperService.mapperParams(json, method);
        return method.invoke(classObject, params.toArray());
    }
}
