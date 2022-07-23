package inspect.invoke.ivm.factory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import inspect.invoke.ivm.dto.InvokeRequest;
import inspect.invoke.ivm.dto.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class InvokeController {

    private final AutowireCapableBeanFactory beanFactory;

    @GetMapping("/invoke")
    public Object invokeControllerMethod(@RequestBody InvokeRequest invokeRequest) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object classObject = Class.forName(invokeRequest.getClassLocation()).getDeclaredConstructor().newInstance();
        Class<?> aClass = classObject.getClass();
        initBeans(classObject, aClass);
        Method methodOfClass = Arrays.stream(aClass.getMethods())
                .filter(method -> method.getName().equals(invokeRequest.getMethodName()))
                .collect(Collectors.toList())
                .get(0);
        return methodOfClass.invoke(classObject, getParams(invokeRequest));
    }

    public Object[] getParams(InvokeRequest invokeRequest) {

        Gson gson = new Gson();

        List<Param> params = invokeRequest.getArgs().stream()
                .map(invokeArg -> {
                    Class<?> currentClass = invokeArg.getClassLocation();
                    Param param = new Param();

                    if (String.class.equals(currentClass)) {
                        param.add(invokeArg.getArg());
                        return param;
                    } else if (Integer.class.equals(currentClass)) {
                        param.add(Integer.parseInt((String) invokeArg.getArg()));
                        return param;
                    } else if (Double.class.equals(currentClass)) {
                        param.add(Double.parseDouble((String) invokeArg.getArg()));
                        return param;
                    } else if (Float.class.equals(currentClass)) {
                        param.add(Float.parseFloat((String) invokeArg.getArg()));
                        return param;
                    }

                    JsonElement jsonElement = gson.toJsonTree(invokeArg.getArg());
                    return getParam(jsonElement, currentClass, gson);

                }).collect(Collectors.toList());

        return params.stream().map(param -> {
            if(param.isAlone()) {
                return param.getObject();
            }
            return param.getObjects();
        }).toArray();
    }

    public Param getParam (JsonElement jsonElement, Class<?> currentClass, Gson gson) {
        Param param = new Param();
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<Object> objectList = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                objectList.add(gson.fromJson(element, currentClass));
            }
            param.add(objectList);
        } catch (IllegalStateException e) {
            param.add(gson.fromJson(jsonElement, currentClass));
        }
        return param;
    }

    public void initBeans(Object object, Class<?> aClass) {
        List<Field> fields = Arrays.asList(aClass.getDeclaredFields());
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
