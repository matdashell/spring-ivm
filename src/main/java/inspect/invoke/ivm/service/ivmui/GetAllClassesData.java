package inspect.invoke.ivm.service.ivmui;

import inspect.invoke.ivm.annotation.IvmMethod;
import inspect.invoke.ivm.dto.ClassData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllClassesData {

    private final FindAllAnnotedMethodsService findAllIvmAnnotedMethodsService;

    public List<ClassData> get(List<Object> objects) {
        return objects.stream()
                .map(object -> {
                    List<Method> methods = findAllIvmAnnotedMethodsService.get(object);
                    return ClassData.builder()
                            .name(object.getClass().getName())
                            .methods(getAllMethodsNames(methods))
                            .build();
                })
                .collect(Collectors.toList());
    }

    //TODO: 1.1 FRAGMENTAR
    private List<String> getAllMethodsNames(List<Method> methods) {
        return methods.stream()
                .map(method -> method.getAnnotation(IvmMethod.class).name())
                .collect(Collectors.toList());
    }
}
