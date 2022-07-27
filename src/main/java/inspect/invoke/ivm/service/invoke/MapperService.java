package inspect.invoke.ivm.service.invoke;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import inspect.invoke.ivm.dto.MethodData;
import inspect.invoke.ivm.exception.IvmJsonOrImplNotFound;
import inspect.invoke.ivm.util.Utils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapperService {

    public List<Object> mapperParams(MethodData methodData, List<Object> jsonParams, List<Object> implParams) {
        Gson gson = new Gson();
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < jsonParams.size(); i++) {
            Class<?> aClass = methodData.getParamTypes()[i];
            JsonElement jsonElement = gson.toJsonTree(jsonParams.get(i));
            Object jsonObject = gson.fromJson(jsonElement, aClass);
            result.add(mapper(jsonObject, implParams.get(i), aClass));
        }
        return result;
    }

    private Object mapper(Object jsonObject, Object impl, Class<?> aClass) {
        if (jsonObject == null && impl == null) throw new IvmJsonOrImplNotFound();
        if (Utils.nullOrPrimitive(impl, aClass)) return jsonObject;
        if (jsonObject == null) return impl;
        Field[] jsonFields = jsonObject.getClass().getDeclaredFields();
        for (Field jsonField : jsonFields) {
            jsonField.setAccessible(true);
            try {
                Object value = jsonField.get(jsonObject);
                if (value != null) {
                    Field fieldOfImpl = aClass.getDeclaredField(jsonField.getName());
                    fieldOfImpl.setAccessible(true);
                    fieldOfImpl.set(impl, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return impl;
    }
}
