package inspect.invoke.ivm.service.invoke;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import inspect.invoke.ivm.dto.MethodData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperService {

    public List<Object> mapperParams(List<Object> objectList, MethodData methodData) {
        Gson gson = new Gson();
        List<Object> result = new ArrayList<>();
        for(int i = 0; i < objectList.size(); i++) {
            JsonElement jsonElement = gson.toJsonTree(objectList.get(i));
            Object temp = gson.fromJson(jsonElement, methodData.getParamTypes()[i]);
            result.add(temp);
        }
        return result;
    }
}
