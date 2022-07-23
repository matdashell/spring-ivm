package inspect.invoke.ivm.factory;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class InvokeController {

    private final AutowireCapableBeanFactory beanFactory;

    @SuppressWarnings(value = "all")
    @GetMapping("/invoke")
    public Object invokeControllerMethod(@RequestBody InvokeRequest invokeRequest) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object classObject = Class.forName(invokeRequest.getClassLocation()).getDeclaredConstructor().newInstance();

        initBeans(classObject);

        Class<?> aClass = classObject.getClass();

        Method methodOfClass = Arrays.stream(aClass.getMethods())
                .filter(method -> method.getName().equals(invokeRequest.getMethodName()))
                .collect(Collectors.toList())
                .get(0);

        List<Object> params = invokeRequest.getArgs().stream()
                .map(invokeArg -> {
                    Class<?> currentClass = invokeArg.getClassLocation();
                    if (currentClass.equals(String.class)) {
                        return invokeArg.getArg();
                    } else {
                        return new Gson().fromJson(invokeArg.getArg(), currentClass);
                    }
                })
                .collect(Collectors.toList());

        return methodOfClass.invoke(classObject, params.toArray());
    }

    public void initBeans(Object object) {
        Class<?> aClass = object.getClass();

        List<Field> fields = List.of(aClass.getDeclaredFields());

        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                if (field.get(object) == null) {
                    Object temp = field.getType().getDeclaredConstructor().newInstance();
                    beanFactory.autowireBean(temp);
                    field.set(object, temp);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InstantiationException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
