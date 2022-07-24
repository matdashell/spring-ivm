package inspect.invoke.ivm.factory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import inspect.invoke.ivm.dto.InvokeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.NamedBeanHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class InvokeController {

    private final AutowireCapableBeanFactory beanFactory;

    @GetMapping("/invoke")
    public Object invokeControllerMethod(@RequestBody InvokeRequest invokeRequest) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object classObject = Class.forName(invokeRequest.getClassLocation()).getDeclaredConstructor().newInstance();
        Class<?> aClass = classObject.getClass();
        initBeans(classObject, aClass);
        Method methodOfClass = aClass.getDeclaredMethod(invokeRequest.getMethodName(), invokeRequest.getMethodsParameters());
        return methodOfClass.invoke(classObject, getParams(invokeRequest));
    }

    public Object[] getParams(InvokeRequest invokeRequest) {

        Gson gson = new Gson();

        return invokeRequest.getArgs().stream()
                .map(invokeArg -> {
                    Class<?> currentClass = invokeArg.getAbsoluteClass();

                    if (String.class.equals(currentClass)) {
                        return invokeArg.getArg();

                    } else if (Integer.class.equals(currentClass)) {
                        return Integer.parseInt((String) invokeArg.getArg());

                    } else if (Double.class.equals(currentClass)) {
                        return Double.parseDouble((String) invokeArg.getArg());

                    } else if (Float.class.equals(currentClass)) {
                        return Float.parseFloat((String) invokeArg.getArg());
                    }

                    JsonElement jsonElement = gson.toJsonTree(invokeArg.getArg());
                    return getParam(jsonElement, currentClass, gson);

                }).toArray();
    }

    public Object getParam(JsonElement jsonElement, Class<?> currentClass, Gson gson) {
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<Object> objectList = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                objectList.add(gson.fromJson(element, currentClass));
            }
            return objectList;
        } catch (IllegalStateException e) {
            return gson.fromJson(jsonElement, currentClass);
        }
    }

    public void initBeans(Object object, Class<?> aClass) {
        List<Field> fields;
        try {
            fields = Arrays.asList(aClass.getDeclaredFields());
        } catch (Exception e) {
            return;
        }
        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                if (field.get(object) == null) {
                    NamedBeanHolder<?> namedBeanHolder = beanFactory.resolveNamedBean(field.getType());
                    Object temp = namedBeanHolder.getBeanInstance();
                    field.set(object, temp);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
